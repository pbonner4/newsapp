package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.ArticleContent;
import newsApp.model.Trending;
import newsApp.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class JdbcArticleDao implements ArticleRetrieveDao{

    private ArticleService articleService;
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertNbaArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertNflArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertNhlArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertMmaArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertBusinessArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertMovieArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertTvArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertMusicArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertZeroHedgeArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertPollackArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertLeftWingArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertCenterArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    public void insertRightWingArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified) {
        String sql = "INSERT INTO articles (source_id, source_name, author, title, description, url, url_to_image, published_at, content, category, category_specified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (title) DO UPDATE SET source_name = EXCLUDED.source_name, author = EXCLUDED.author, title = EXCLUDED.title, description = EXCLUDED.description, url = EXCLUDED.url, url_to_image = EXCLUDED.url_to_image, published_at = EXCLUDED.published_at, content = EXCLUDED.content, category = EXCLUDED.category, category_specified = EXCLUDED.category_specified;";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(publishedAt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sourceId, sourceName, author, title, description, url, urlToImage, sqlTimestamp, content, category, categorySpecified);
    }

    @Override
    public List<Article> getNbaArticles() {

        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'nba' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getNflArticles() {

        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'nfl' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getNhlArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'nhl' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getMmaArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'mma' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getBusinessArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category = 'business' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getMovieArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'movies' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getTvArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'television' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getMusicArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'music' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }


    @Override
    public List<Article> getLeftWingArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'left_wing' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getCenterArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'center' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getRightWingArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category_specified = 'right_wing' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> getPoliticalArticles() {
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles WHERE category = 'politics' ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public List<Trending> getTrendingArticles(){
        List<Trending> trending = new ArrayList<>();
        String sql = "SELECT lp.article_id, a.published_at, a.title, a.url, a.url_to_image, a.category, a.category_specified, a.description, COUNT(lp.article_id) as likes_count FROM liked_posts lp INNER JOIN articles a ON lp.article_id = a.article_id WHERE lp.liked = true AND a.published_at >= (CURRENT_DATE - INTERVAL '5' DAY) GROUP BY lp.article_id, a.published_at, a.title, a.url, a.url_to_image, a.category, a.category_specified, a.description ORDER BY likes_count DESC LIMIT 20;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()){
            trending.add(mapRowFromTrending(rowSet));
        }

        return trending;
    }

    @Override
    public List<Article> getAllArticles(){
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * FROM articles ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }
    @Override
    public List<ArticleContent> getAllArticleContent(){
        List<ArticleContent> articles = new ArrayList<>();

        String sql = "SELECT * FROM article_content;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            articles.add(mapRowFromArticleContent(rowSet));
        }

        return articles;
    }

    @Override
    public List<Article> searchForArticles(String searchTerm){
        List<Article> articles = new ArrayList<>();

        String sql = "SELECT * from articles\n" +
                "where title ILIKE ?\n" +
                "ORDER BY published_at DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, '%' + searchTerm + '%');

        while (rowSet.next()){
            articles.add(mapRowFromArticles(rowSet));
        }

        return articles;
    }

    @Override
    public void insertArticleContent(){
        List<Article> articles = getAllArticles();

        for (Article article : articles) {
            try {
                String url = article.getUrl();
                Document doc = Jsoup.connect(url).get();
                String title = doc.title();
                String content = doc.text();

                String sql = "INSERT INTO article_content (article_id, title, text) VALUES (?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                jdbcTemplate.update(sql, article.getId(), title, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ArticleContent> getArticleContent(String articleId){
        List<ArticleContent> content = new ArrayList<>();

        String sql = "SELECT * FROM article_content WHERE article_id = ?;";
        articleId = articleId.replaceAll("\\{", "").replaceAll("\\}", "");

        int articleIdConversion = Integer.parseInt(articleId);

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, articleIdConversion);

        while (rowSet.next()) {
            content.add(mapRowFromArticleContent(rowSet));
        }

        return content;

    }



    @Override
    public List<Article> getContentBasedCuratedArticles(int userId){
        List<Article> filteredArticles = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "http://127.0.0.1:8000/rec/cb/user/{userId}/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>(){},
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



    private ArticleContent mapRowFromArticleContent(SqlRowSet rs){
        ArticleContent content = new ArticleContent();
        content.setArticle_id(rs.getInt("article_id"));
        content.setTitle(rs.getString("title"));
        content.setText(rs.getString("text"));
        content.setUrl(rs.getString("url"));
        content.setUrlToImage(rs.getString("url_to_image"));
        content.setCategorySpecified(rs.getString("category_specified"));
        content.setCategory(rs.getString("category"));
        return content;
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

    private Trending mapRowFromTrending(SqlRowSet rs){
        Trending trending = new Trending();
        trending.setArticleId(rs.getInt("article_id"));
        trending.setPublishedAt(String.valueOf(rs.getDate("published_at")));
        trending.setTitle(rs.getString("title"));
        trending.setUrl(rs.getString("url"));
        trending.setUrlToImage(rs.getString("url_to_image"));
        trending.setCategory(rs.getString("category"));
        trending.setCategorySpecified(rs.getString("category_specified"));
        trending.setLikeCount(rs.getInt("likes_count"));
        return trending;
    }

//    private Article mapFromResult(Result result){
//        Article articles = new Article();
//        articles.setName(result.getSource());
//        articles.setAuthor(result.getAuthor());
//        articles.setTitle(result.getTitle());
//        articles.setDescription(result.getDescription());
//        articles.setUrl(result.getUrl());
//        articles.setUrlToImage(result.getUrlToImage());
//        articles.setPublishedAt(result.getPublishedAt());
//        articles.setContent(result.getContent());
//        return articles;
//    }

}
