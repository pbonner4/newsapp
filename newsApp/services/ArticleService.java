package newsApp.services;

import newsApp.dao.ArticleRetrieveDao;
import newsApp.dao.JdbcArticleDao;
import newsApp.model.articleTemplate.Result;
import newsApp.util.MD5Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import newsApp.model.articleTemplate.JsonBody;

import javax.annotation.PostConstruct;

@Service
public class ArticleService {

    public static String NEWS_API_DOMAIN_URL = "https://newsapi.org/v2/everything?";

    @Value("${newsApiKey}")
    private String newsApiKey;
    private String NEWS_API_KEY;

    @PostConstruct
    private void initializeNewsApiKey() {
        NEWS_API_KEY = "apikey=" + newsApiKey;
    }

    private final String NBA = "nba";
    private final String NFL = "nfl";
    private final String NHL = "nhl";
    private final String MMA = "mma";
    private final String SPORTS = "sports";
    private final String BUSINESS = "business";
    private final String ENTERTAINMENT = "entertainment";
    private final String MOVIES = "movies";
    private final String TV = "television";

    private final String MUSIC = "music";
    private final String POLITICS = "politics";
    private final String RIGHT_WING = "right_wing";
    private final String LEFT_WING = "left_wing";
    private final String CENTER = "center";

    private RestTemplate restTemplate = new RestTemplate();

    private final JdbcArticleDao articleDAO;

    @Autowired
    public ArticleService(JdbcArticleDao articleDAO) {
        this.articleDAO = articleDAO;
    }

    public String getPostManAddress(){
      return NEWS_API_DOMAIN_URL;
    }

    //SPORTS
    public List<Result> getNbaArticles() {
        JsonBody jsonBody =  restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "q=nba&sortBy=publishedAt&domains=cbssports.com, nbcsports.com", JsonBody.class);

        assert jsonBody != null;

        List<Result> results = jsonBody.getArticles();
        for (Result result : results) {
            articleDAO.insertNbaArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), SPORTS, NBA);
        }
        return results;
    }






    public List<Result> getNflArticles() {
        System.out.println(NEWS_API_KEY);
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "q=nfl&sortBy=publishedAt&domains=nfl.com, cbssports.com", JsonBody.class);

        assert jsonBody != null;

        List<Result> results = jsonBody.getArticles();
        for (Result result : results) {
            articleDAO.insertNflArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), SPORTS, NFL);
        }

        return results;
    }

    public List<Result> getNhlArticles() {
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=nhl&sortBy=publishedAt&domains=cbssports.com, nhl.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();
        for (Result result : results) {
            articleDAO.insertNhlArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), SPORTS, NHL);
        }

        return results;
    }

    public List<Result> getMmaArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=ufc&sortBy=publishedAt&domains=mmafighting.com, mmajunkie.com, espn.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertMmaArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), SPORTS, MMA);
        }

        return results;
    }

    //BUSINESS
    public List<Result> getBusinessArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=business&sortBy=publishedAt&domains=economist.com, investing.com, wsj.com, marketwatch.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertBusinessArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), BUSINESS, null);
        }

        return results;

    }

    //entertainment
    public List<Result> getMovieArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=movies&sortBy=publishedAt&domains=ew.com, eonline.com, variety.com, hollywoodreporter.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertMovieArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), ENTERTAINMENT, MOVIES);
        }

        return results;
    }

    public List<Result> getTvArticles() {
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=television&sortBy=publishedAt&domains=ew.com, eonline.com, variety.com, hollywoodreporter.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertTvArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), ENTERTAINMENT, TV);
        }

        return results;

    }

    public List<Result> getMusicArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=music&sortBy=publishedAt&domains=variety.com, hollywoodreporter.com, hiphopwired.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertMusicArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), ENTERTAINMENT, MUSIC);
        }

        return results;

    }

    //POLITICS
    public List<Result> getZeroHedgeArticles() {
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=&sortBy=publishedAt&domains=zerohedge.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertZeroHedgeArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), POLITICS, CENTER);
        }

        return results;
    }

    public List<Result> getPollackArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=Joel%20Pollak&sortBy=publishedAt&domains=breitbart.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertPollackArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), POLITICS, RIGHT_WING);
        }

        return results;
    }

    public List<Result> getLeftWingArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=politics&sortBy=publishedAt&domains=slate.com, newyorker.com, politico.com, time.com, apnews.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            if(!result.getUrl().contains("/newsletters/")) {
                articleDAO.insertLeftWingArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), POLITICS, LEFT_WING);
            }
        }

        return results;

    }

    public List <Result> getCenterArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=politics&sortBy=publishedAt&domains=wsj.com, thehill.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertCenterArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), POLITICS, CENTER);
        }

        return results;
    }

    public List <Result> getRightWingArticles(){
        JsonBody jsonBody = restTemplate.getForObject(NEWS_API_DOMAIN_URL + NEWS_API_KEY + "&q=politics&sortBy=publishedAt&domains=thegatewaypundit.com, thefederalist.com, nypost.com,  theamericanconservative.com, reason.com, theblaze.com", JsonBody.class);

        List<Result> results = jsonBody.getArticles();

        for (Result result : results) {
            articleDAO.insertRightWingArticles(result.getSource().getId(), result.getSource().getName(), result.getAuthor(), result.getTitle(), result.getDescription(), result.getUrl(), result.getUrlToImage(), result.getPublishedAt(), result.getContent(), POLITICS, RIGHT_WING);
        }

        return results;

    }


    public String createHash(){


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String time = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss").format(timestamp);

        String toHash = time+"befe5220b141aff556075db0c363e08503caef6c"+"a16369667ffd8d775ae7193fca1f945e";

        String hashedString = MD5Utils.hash(toHash);

        String fullAddress = "?ts="+time+"&apikey=a16369667ffd8d775ae7193fca1f945e&hash="+hashedString;

        return fullAddress;
    }

}
