package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.Trending;
import newsApp.model.articleTemplate.Result;
import newsApp.services.ArticleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcLoggerDao implements LoggerDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLoggerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUserEvent(Date created, int userId, String articleId, String event, String sessionId){
            String sql = "INSERT INTO collector_log (created, user_id, article_id, event, session_id) " +
                    "VALUES (current_timestamp, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, userId, articleId, event, sessionId);
    }
}
