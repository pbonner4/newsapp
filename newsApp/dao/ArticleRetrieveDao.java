package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.ArticleContent;
import newsApp.model.Trending;

import java.util.List;

public interface ArticleRetrieveDao {

    void insertNbaArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertNflArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertNhlArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertMmaArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertBusinessArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertMovieArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertTvArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertMusicArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertZeroHedgeArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertPollackArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertLeftWingArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertCenterArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);

    void insertRightWingArticles(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String category, String categorySpecified);


    void insertArticleContent();

    public List<ArticleContent> getArticleContent(String articleId);
    public List<ArticleContent> getAllArticleContent();

    public List<Article> getAllArticles();
    public List<Article> getNbaArticles();

    public List<Article> getNflArticles();

    public List<Article> getNhlArticles();

    public List<Article> getMmaArticles();

    public List<Article> getBusinessArticles();

    public List<Article> getMovieArticles();

    public List<Article> getTvArticles();

    public List<Article> getMusicArticles();

    public List<Article> getLeftWingArticles();

    public List<Article> getCenterArticles();

    public List<Article> getRightWingArticles();

    public List<Article> searchForArticles(String searchTerm);

    public List<Article> getPoliticalArticles();
    public List<Trending> getTrendingArticles();

    public List<Article> getContentBasedCuratedArticles(int userId);

}
