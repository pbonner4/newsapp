package newsApp.dao;

import newsApp.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserPreferenceDao implements UserPreferenceDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserPreferenceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void buildUserPreferenceProfile(UserPreferenceProfile userPreferenceProfile, int user_id) {

        String sql = "INSERT INTO user_subject_preferences (user_id, business, politics, right_wing, left_wing, center, sports, nfl, nba, nhl, mma, entertainment, movies, television, music) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        jdbcTemplate.update(sql, user_id, userPreferenceProfile.isBusiness(), userPreferenceProfile.isPolitics(), userPreferenceProfile.isRight_wing(), userPreferenceProfile.isLeft_wing(), userPreferenceProfile.isCenter(), userPreferenceProfile.isSports(),
                userPreferenceProfile.isNfl(), userPreferenceProfile.isNba(), userPreferenceProfile.isNhl(), userPreferenceProfile.isMma(), userPreferenceProfile.isEntertainment(), userPreferenceProfile.isMovies(), userPreferenceProfile.isTelevision(),
        userPreferenceProfile.isMusic());

    }

    @Override
    public void updatePreferenceProfile(UserPreferenceProfile userPreferenceProfile, int user_id){
        String sql = "INSERT INTO user_subject_preferences (user_id, business, politics, right_wing, left_wing, center, sports, nfl, nba, nhl, mma, entertainment, movies, television, music) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (user_id) DO UPDATE SET " +
                "business = excluded.business, politics = excluded.politics, right_wing = excluded.right_wing, left_wing = excluded.left_wing, center = excluded.center, sports = excluded.sports, " +
                "nfl = excluded.nfl, nba = excluded.nba, nhl = excluded.nhl, mma = excluded.mma, entertainment = excluded.entertainment, movies = excluded.movies, television = excluded.television, music = excluded.music;";

        jdbcTemplate.update(sql, user_id, userPreferenceProfile.isBusiness(), userPreferenceProfile.isPolitics(), userPreferenceProfile.isRight_wing(), userPreferenceProfile.isLeft_wing(), userPreferenceProfile.isCenter(), userPreferenceProfile.isSports(),
                userPreferenceProfile.isNfl(), userPreferenceProfile.isNba(), userPreferenceProfile.isNhl(), userPreferenceProfile.isMma(), userPreferenceProfile.isEntertainment(), userPreferenceProfile.isMovies(), userPreferenceProfile.isTelevision(),
                userPreferenceProfile.isMusic());
    }

    @Override
    public void likeAPost(Like like, int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "INSERT INTO liked_posts(user_id, article_id, liked) VALUES (?,?,?);";
        jdbcTemplate.update(sql2, userId, articleId, like.isLiked());
    }

    @Override
    public List<Article> hasLikedAPostCheck(int userId){
        List<Article> likes = new ArrayList<>();

        String sql = "SELECT * from articles\n" +
                "INNER JOIN liked_posts ON articles.article_id = liked_posts.article_id\n" +
                "where user_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            likes.add(mapRowFromArticles(rowSet));
        }

        return likes;
    }

    @Override
    public void unlikeAPost(int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "DELETE FROM liked_posts WHERE user_id = ? AND article_id = ?;";
        jdbcTemplate.update(sql2, userId, articleId);
    }

    @Override
    public void dislikeAPost(Dislike like, int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "INSERT INTO disliked_posts(user_id, article_id, disliked) VALUES (?,?,?);";
        jdbcTemplate.update(sql2, userId, articleId, like.isDisliked());
    }

    @Override
    public List<Article> listDislikes(int userId){
        List<Article> dislikes = new ArrayList<>();

        String sql = "SELECT * from articles\n" +
                "INNER JOIN disliked_posts ON articles.article_id = disliked_posts.article_id\n" +
                "where user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            dislikes.add(mapRowFromArticles(rowSet));
        }

        return dislikes;
    }


    public void undislikeAPost(int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "DELETE FROM disliked_posts WHERE user_id = ? AND article_id = ?;";
        jdbcTemplate.update(sql2, userId, articleId);
    }

    @Override
    public void saveForLater(SaveForLater later, int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "INSERT INTO save_for_later(user_id, article_id, save_for_later) VALUES (?,?,?);";
        jdbcTemplate.update(sql2, userId, articleId, later.isSaveForLater());
    }

    @Override
    public List<Article> listSaveForLater(int userId){
        List<Article> saved = new ArrayList<>();

        String sql = "SELECT * from articles\n" +
                "INNER JOIN save_for_later ON articles.article_id = save_for_later.article_id\n" +
                "where user_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            saved.add(mapRowFromArticles(rowSet));
        }

        return saved;
    }


    public void removeFromSaveForLater(int userId, int articleId){
        String sql1 = "SELECT 1 FROM articles WHERE article_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, articleId);

        String sql2 = "DELETE FROM save_for_later WHERE user_id = ? AND article_id = ?;";
        jdbcTemplate.update(sql2, userId, articleId);
    }

    private Like mapRowFromLikedPosts(SqlRowSet rs){
        Like likes = new Like();
        likes.setUser_id(rs.getInt("user_id"));
        likes.setArticle_id(rs.getInt("article_id"));
        likes.setLiked(rs.getBoolean("liked"));

        return likes;
    }
    private Dislike mapRowFromDislikedPosts(SqlRowSet rs){
        Dislike dislikes = new Dislike();
        dislikes.setUser_id(rs.getInt("user_id"));
        dislikes.setArticle_id(rs.getInt("article_id"));
        dislikes.setDisliked(rs.getBoolean("disliked"));

        return dislikes;
    }
    private SaveForLater mapRowFromSaveForLater(SqlRowSet rs){
        SaveForLater saved = new SaveForLater();
        saved.setUser_id(rs.getInt("user_id"));
        saved.setArticle_id(rs.getInt("article_id"));
        saved.setsaveForLater(rs.getBoolean("save_for_later"));
        return saved;
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

