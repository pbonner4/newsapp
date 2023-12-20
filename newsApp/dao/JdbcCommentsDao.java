package newsApp.dao;

import newsApp.model.Comment;
import newsApp.model.Trending;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import newsApp.dao.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Date;

@Repository
public class JdbcCommentsDao implements CommentsDao {

    private final JdbcTemplate jdbcTemplate;

    private JdbcArticleDao articleDao;

    public JdbcCommentsDao(JdbcTemplate jdbcTemplate, JdbcArticleDao articleDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleDao = articleDao;
    }


    @Override
    public void postComment(int articleId, String username, Comment comment) {
        String sql = "INSERT INTO comments (article_id, username, comment_id, comment, time_commented) " +
                "VALUES (?, ?, ?, ?, current_timestamp)";
        jdbcTemplate.update(sql, articleId, username, comment.getComment_id(), comment.getComment());
    }

    @Override
    public List<Comment> getCommentsOnArticle(int articleId){
        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT * FROM comments WHERE article_id = ? GROUP BY comment_id,id ORDER BY comment_id, time_commented ASC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, articleId);

        while (rowSet.next()) {
            comments.add(mapRowFromComment(rowSet));
        }

        return comments;
    }

    private Comment mapRowFromComment(SqlRowSet rs){
        Comment comments = new Comment();
        comments.setId(rs.getInt("id"));
        comments.setArticle_id(rs.getInt("article_id"));
        comments.setUsername(rs.getString("username"));
        comments.setComment_id(rs.getString("comment_id"));
        comments.setComment(rs.getString("comment"));
        comments.setTime_commented(rs.getTimestamp("time_commented"));
        return comments;
    }
}
