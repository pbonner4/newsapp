package newsApp.dao;

import newsApp.model.Comment;

import java.util.Date;
import java.util.List;

public interface CommentsDao {

    void postComment(int articleId, String username, Comment comment);

    public List<Comment> getCommentsOnArticle(int articleId);

}
