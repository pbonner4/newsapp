package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.LdaSimilarity;
import newsApp.model.UserRating;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class JdbcContentBasedRecommenderDao implements RecommendationSystemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcContentBasedRecommenderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Article> recommendItemsByRatings(int userId, UserRating userRating, int limit) {


        double minSimilarity = 0.1;
        int maxCandidates = 100;

        String user_id = String.valueOf(userId);


        List<UserRating> userRatings = new ArrayList<>();

        List<LdaSimilarity> LdaSimilarities = new ArrayList<>();

        String sql = "select * from analytics_rating WHERE user_id = ? ORDER BY rating DESC;";
        String sql2 = "select * from lda_similarity;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, user_id);
        SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql2);

        while (rowSet.next()) {
            userRatings.add(mapRowFromRatings(rowSet));
        }
        while (rowSet2.next()) {
            LdaSimilarities.add(mapRowFromLdaSimilarity(rowSet2));
        }

        Map<String, Double> articleIds = userRatings.stream()
                .collect(Collectors.toMap(
                        article -> article.getArticle_id(),
                        article -> article.getRating()
                ));
        double userMean = articleIds.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        List<LdaSimilarity> sims = LdaSimilarities.stream()
                .filter(sim -> articleIds.containsKey(sim.getSource()))
                .filter(sim -> !articleIds.containsKey(sim.getTarget()))
                .filter(sim -> sim.getSimilarity() > minSimilarity)
                .sorted(Comparator.comparing(LdaSimilarity::getSimilarity).reversed())
                .limit(maxCandidates)
                .collect(Collectors.toList());

        Set<String> targets = sims.stream()
                .map(LdaSimilarity::getTarget)
                .filter(target -> !target.isEmpty())
                .collect(Collectors.toSet());

        Map<String, Object> recommendations = new HashMap<>();

        for (String target : targets) {
            BigDecimal pre = BigDecimal.ZERO;
            double simSum = 0;

            List<LdaSimilarity> ratedItems = sims.stream()
                    .filter(sim -> sim.getTarget().equals(target))
                    .collect(Collectors.toList());

            if (ratedItems.size() > 0) {
                for (LdaSimilarity ratedItem : ratedItems) {
                    BigDecimal r = new BigDecimal(articleIds.get(ratedItem.getSource()) - userMean);
                    BigDecimal sim = new BigDecimal(ratedItem.getSimilarity());
                    pre = pre.add(sim.multiply(r));
                    simSum += ratedItem.getSimilarity();
                }
                if (simSum > 0) {
                    BigDecimal prediction = new BigDecimal(userMean).add(new BigDecimal(String.valueOf(pre)).divide(new BigDecimal(simSum), RoundingMode.HALF_UP));
                    Map<String, Object> targetRec = new HashMap<>();
                    targetRec.put("prediction", prediction);
                    List<String> simItems = ratedItems.stream().map(LdaSimilarity::getSource).collect(Collectors.toList());
                    targetRec.put("sim_items", simItems);
                    recommendations.put(target, targetRec);
                }
            }
        }

        List<Map.Entry<String, Object>> sortedRecs = new ArrayList<>(recommendations.entrySet());
        sortedRecs.sort((a, b) -> -((BigDecimal) ((Map<String, Object>) a.getValue()).get("prediction")).compareTo((BigDecimal) ((Map<String, Object>) b.getValue()).get("prediction")));

        List<Article> articlesToRecommend = new ArrayList<>();

        List<String> articleIdsToRecommend = sortedRecs.stream()
                .map(article -> (String) article.getKey())
                .collect(Collectors.toList());


        String sql3 = "SELECT * FROM articles WHERE article_id IN (" +
                articleIdsToRecommend.stream()
                        .map(article_id -> "'" + article_id + "'")
                        .collect(Collectors.joining(",")) + ");";

        SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql3, new Object[]{});

        while (rowSet3.next()) {
            articlesToRecommend.add(mapRowFromArticles(rowSet3));
        }


        return articlesToRecommend;

    }

    @Override
    public List<Article> recommendItemsByArticle(String articleId) {
        List<Article> filteredArticles = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "http://127.0.0.1:8000/rec/cb/item/{articleId}/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                articleId
        );


        Map<String, Object> responseMap = response.getBody();

        List<Map<String, Object>> filteredResponseMap = (List<Map<String, Object>>) responseMap.get("data");


        List<String> articleIds = filteredResponseMap.stream()
                .sorted((a, b) -> Double.compare(Double.parseDouble(b.get("similarity").toString()), Double.parseDouble(a.get("similarity").toString())))
                .map(article -> (String) article.get("target"))
                .collect(Collectors.toList());


        String sql3 = "SELECT * FROM articles WHERE article_id IN (" +
                articleIds.stream()
                        .map(article_id -> "'" + article_id + "'")
                        .collect(Collectors.joining(",")) + ") " +
                "ORDER BY ARRAY_POSITION(ARRAY[" +
                articleIds.stream()
                        .map(article_id -> "'" + article_id + "'")
                        .collect(Collectors.joining(",")) + "], article_id::text);";


        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql3, new Object[]{});

        while (rowSet.next()) {
            filteredArticles.add(mapRowFromArticles(rowSet));
        }

        return filteredArticles;
    }

    @Override
    public List<Article> matrixFactorizationArticles(int userId) {
        List<Article> filteredArticles = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "http://127.0.0.1:8000/rec/funk/user/{userId}/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                userId
        );

        Map<String, Object> responseMap = response.getBody();

        List<List<Object>> articleData = (List<List<Object>>) responseMap.get("data");

        if (!articleData.isEmpty()) {
            List<String> articleIds = articleData.stream()
                    .map(article -> (String) article.get(0))
                    .collect(Collectors.toList());

            String sql = "SELECT * FROM articles WHERE article_id IN (" +
                    articleIds.stream()
                            .map(article_id -> "'" + article_id + "'")
                            .collect(Collectors.joining(",")) + ") " +
                    "ORDER BY ARRAY_POSITION(ARRAY[" +
                    articleIds.stream()
                            .map(article_id -> "'" + article_id + "'")
                            .collect(Collectors.joining(",")) + "], article_id::text);";


            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, new Object[]{});

            while (rowSet.next()) {
                filteredArticles.add(mapRowFromArticles(rowSet));
            }
        }
        return filteredArticles;
    }

    @Override
    public List<Article> fwlsRecommendations(int userId) {
        List<Article> filteredArticles = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "http://127.0.0.1:8000/rec/fwls/user/{userId}/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                },
                userId
        );

        Map<String, Object> responseMap = response.getBody();

        List<List<Object>> articleData = (List<List<Object>>) responseMap.get("data");

        if (!articleData.isEmpty()) {
            List<String> articleIds = articleData.stream()
                    .map(article -> (String) article.get(0))
                    .collect(Collectors.toList());

            String sql = "SELECT * FROM articles WHERE article_id IN (" +
                    articleIds.stream()
                            .map(article_id -> "'" + article_id + "'")
                            .collect(Collectors.joining(",")) + ") " +
                    "ORDER BY ARRAY_POSITION(ARRAY[" +
                    articleIds.stream()
                            .map(article_id -> "'" + article_id + "'")
                            .collect(Collectors.joining(",")) + "], article_id::text);";


            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, new Object[]{});

            while (rowSet.next()) {
                filteredArticles.add(mapRowFromArticles(rowSet));
            }
        }
        return filteredArticles;
    }


    private UserRating mapRowFromRatings(SqlRowSet rs){
        UserRating userRating = new UserRating();
        userRating.setUser_id(rs.getString("user_id"));
        userRating.setArticle_id(rs.getString("article_id"));
        userRating.setRating(rs.getDouble("rating"));
        userRating.setRating_timestamp(rs.getTimestamp("rating_timestamp"));


        return userRating;
    }

    private LdaSimilarity mapRowFromLdaSimilarity(SqlRowSet rs){
        LdaSimilarity similarity = new LdaSimilarity();
        similarity.setCreated(rs.getDate("created"));
        similarity.setSource(rs.getString("source"));
        similarity.setTarget(rs.getString("target"));
        similarity.setSimilarity(rs.getDouble("similarity"));


        return similarity;
    }

    private Article mapRowFromArticles(SqlRowSet rs){
        Article articles = new Article();
        articles.setId(rs.getInt("article_id"));
        articles.setName(rs.getString("source_name"));
        articles.setAuthor(rs.getString("author"));
        articles.setTitle(rs.getString("title"));
        articles.setDescription(rs.getString("description"));
        articles.setUrl(rs.getString("url"));
        articles.setUrlToImage(rs.getString("url_to_image"));
        articles.setPublishedAt(String.valueOf(rs.getDate("published_at")));
        articles.setCategory(rs.getString("category"));
        articles.setCategorySpecified(rs.getString("category_specified"));
        articles.setContent(rs.getString("content"));
        return articles;
    }

}
