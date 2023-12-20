package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.*;
import newsApp.model.*;
import newsApp.model.articleTemplate.Result;
import newsApp.services.ArticleService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableScheduling
@CrossOrigin
@RestController
public class ArticleController {

    private JdbcArticleDao articleDao;
    private JdbcArticleContentDao contentDao;
    private UserDao userDao;
    private JdbcContentBasedRecommenderDao contentBasedRecommenderDao;
    @Autowired
    private ArticleService articleService;

    public ArticleController(JdbcArticleDao article, JdbcArticleContentDao contentDao, JdbcUserDao userDao, JdbcContentBasedRecommenderDao contentBasedRecommenderDao) {
        this.articleDao = article;
        this.contentDao = contentDao;
        this.userDao = userDao;
        this.contentBasedRecommenderDao = contentBasedRecommenderDao;
    }

    @Scheduled(cron = "0 0 */4 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/nba")
    public ResponseEntity<String> insertNbaArticles() {

        List<Result> result = articleService.getNbaArticles();
        contentDao.insertNbaContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/nba", method = RequestMethod.GET)
    public List<Article> listNbaArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getNbaArticles();
    }

    @Scheduled(cron = "0 0 */5 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/nfl")
    public ResponseEntity<String> insertNflArticles() {

        List<Result> result = articleService.getNflArticles();
        contentDao.insertNflContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/nfl", method = RequestMethod.GET)
    public List<Article> listNflArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getNflArticles();
    }

    @Scheduled(cron = "0 0 */6 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/nhl")
    public ResponseEntity<String> insertNhlArticles() {

        List<Result> result = articleService.getNhlArticles();
        contentDao.insertNhlContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/nhl", method = RequestMethod.GET)
    public List<Article> listNhlArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getNhlArticles();
    }
    @Scheduled(cron = "0 0 */7 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/mma")
    public ResponseEntity<String> insertMmaArticles() {

        List<Result> result = articleService.getMmaArticles();
        contentDao.insertMmaContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/mma", method = RequestMethod.GET)
    public List<Article> listMmaArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getMmaArticles();
    }
    @Scheduled(cron = "0 0 */8 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/business")
    public ResponseEntity<String> insertBusinessArticles() {

        List<Result> result = articleService.getBusinessArticles();
        contentDao.insertBusinessContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @PreAuthorize("permitAll()")
    @RequestMapping( path ="/articles/business", method = RequestMethod.GET)
    public List<Article> listBusinessArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getBusinessArticles();
    }

    @Scheduled(cron = "0 0 */9 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/entertainment/movies")
    public ResponseEntity<String> insertMovieArticles() {

        List<Result> result = articleService.getMovieArticles();
        contentDao.insertMovieContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/entertainment/movies", method = RequestMethod.GET)
    public List<Article> listMoviesArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getMovieArticles();
    }

    @Scheduled(cron = "0 0 */11 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/entertainment/television")
    public ResponseEntity<String> insertTvArticles() {

        List<Result> result = articleService.getTvArticles();
        contentDao.insertTvContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/entertainment/television", method = RequestMethod.GET)
    public List<Article> listTvArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getTvArticles();
    }
    @Scheduled(cron = "0 0 */12 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/entertainment/music")
    public ResponseEntity<String> insertMusicArticles() {

        List<Result> result = articleService.getMusicArticles();
        contentDao.insertMusicContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/entertainment/music", method = RequestMethod.GET)
    public List<Article> listMusicArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getMusicArticles();
    }
    @Scheduled(cron = "0 0 */13 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/politics/zerohedge")
    public ResponseEntity<String> insertZeroHedgeArticles() {

        List<Result> result = articleService.getZeroHedgeArticles();
        contentDao.insertCenterContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }


    @Scheduled(cron = "0 0 */14 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/politics/pollack")
    public ResponseEntity<String> insertPollackArticles() {

        List<Result> result = articleService.getPollackArticles();

        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }
    @Scheduled(cron = "0 0 */14 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/politics/left")
    public ResponseEntity<String> insertLeftWingArticles() {
        List<Result> result = articleService.getLeftWingArticles();
        contentDao.insertLeftContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/politics/left", method = RequestMethod.GET)
    public List<Article> listLeftWingArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getLeftWingArticles();
    }

    @Scheduled(cron = "0 0 */15 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/politics/center")
    public ResponseEntity<String> insertCenterArticles() {

        List<Result> result = articleService.getCenterArticles();
        contentDao.insertCenterContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/politics/center", method = RequestMethod.GET)
    public List<Article> listCenterArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getCenterArticles();
    }

    @Scheduled(cron = "0 0 */16 * * *")
    @JsonProperty
    @PostMapping(path = "/articles/politics/right")
    public ResponseEntity<String> insertRightWingArticles() {

        List<Result> result = articleService.getRightWingArticles();
        contentDao.insertRightContent();
        if (result != null) {
            return ResponseEntity.ok("Articles inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting articles");
        }
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/politics/right", method = RequestMethod.GET)
    public List<Article> listRightArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getRightWingArticles();
    }

    @CrossOrigin
    @RequestMapping( path ="/articles/politics", method = RequestMethod.GET)
    public List<Article> listPoliticalArticles(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getPoliticalArticles();
    }

    @CrossOrigin
    @RequestMapping(path = "/articles/trending", method = RequestMethod.GET)
        public List<Trending> listTrendingArticles (Principal principal){
            String signedInUser = principal.getName();
            int userId = userDao.findIdByUsername(signedInUser);
            return articleDao.getTrendingArticles();
        }

    @CrossOrigin
    @RequestMapping(path = "/articles", method = RequestMethod.GET)
    public List<Article> listAllArticles (Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return articleDao.getAllArticles();
    }

    @CrossOrigin
    @RequestMapping(path = "/allArticlesContent", method = RequestMethod.GET)
    public List<ArticleContent> listAllArticlesContent (Principal principal){
        return articleDao.getAllArticleContent();
    }

    @CrossOrigin
    @RequestMapping(path = "/searchArticles/{searchTerm}", method = RequestMethod.GET)
    public List<Article> searchForArticles (@PathVariable String searchTerm){

        return articleDao.searchForArticles(searchTerm);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/article/post/{articleId}", method = RequestMethod.GET)
    public List<ArticleContent> getContent(@PathVariable String articleId) {
        return articleDao.getArticleContent(articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/curatedContentBased", method = RequestMethod.GET)
    public List<Article> getContentBasedArticles(Principal principal) {
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);

        return articleDao.getContentBasedCuratedArticles(userId);
    }
    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/curatedContentBasedInJava", method = RequestMethod.GET)
    public List<Article> getContentBasedArticlesInJava(Principal principal, UserRating userRating) {
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return contentBasedRecommenderDao.recommendItemsByRatings(userId, userRating, 60);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/curatedContentBasedPerArticle/{articleId}", method = RequestMethod.GET)
    public List<Article> getContentBasedArticlesPerArticle(@PathVariable String articleId) {

        articleId = articleId.replaceAll("\\{", "").replaceAll("\\}", "");
        return contentBasedRecommenderDao.recommendItemsByArticle(articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/matrixFactorizationArticles", method = RequestMethod.GET)
    public List<Article> getContentBasedArticlesPerArticle(Principal principal) {

        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);

        return contentBasedRecommenderDao.matrixFactorizationArticles(userId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/fwlsArticles", method = RequestMethod.GET)
    public List<Article> getFwlsArticles(Principal principal) {

        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);

        return contentBasedRecommenderDao.fwlsRecommendations(userId);
    }

}


