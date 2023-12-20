package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.*;
import newsApp.model.Article;
import newsApp.model.Comment;
import newsApp.model.articleTemplate.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class CommentsController {
    private JdbcCommentsDao commentsDao;

    private UserDao userDao;

    public CommentsController(JdbcCommentsDao commentsDao, JdbcUserDao userDao) {
        this.commentsDao = commentsDao;
        this.userDao = userDao;
    }

    @JsonProperty
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin
    @RequestMapping(path = "/articles/{articleId}/postComment", method = RequestMethod.POST)
    public void postAComment(@RequestBody Comment comment, Principal principal, @PathVariable int articleId) {
        String signedInUser = principal.getName();
        commentsDao.postComment(articleId, signedInUser, comment);
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/{articleId}/getComments", method = RequestMethod.GET)
    public List<Comment> listArticleComments(@PathVariable int articleId){
        return commentsDao.getCommentsOnArticle(articleId);
    }

}
