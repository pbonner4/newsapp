package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.UserRating;

import java.util.List;

public interface RecommendationSystemDao {
    List<Article> recommendItemsByRatings(int userId, UserRating userRating, int limit);

    List<Article> recommendItemsByArticle(String articleId);

    List<Article> matrixFactorizationArticles(int userId);

    List<Article> fwlsRecommendations(int userId);

}
