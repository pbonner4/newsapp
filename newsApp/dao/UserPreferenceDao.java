package newsApp.dao;

import newsApp.model.*;

import java.util.List;

public interface UserPreferenceDao {

    void buildUserPreferenceProfile(UserPreferenceProfile userPreferenceProfile, int user_Id);

    void updatePreferenceProfile(UserPreferenceProfile userPreferenceProfile, int user_id);

    void likeAPost(Like like, int userId, int articleId);
    List<Article> hasLikedAPostCheck(int userId);

    List<Article> listDislikes(int userId);
    List<Article> listSaveForLater(int userId);

    void unlikeAPost(int userId, int articleId);

    void dislikeAPost(Dislike like, int userId, int articleId);

    void undislikeAPost(int userId, int articleId);

    void saveForLater(SaveForLater later, int userId, int articleId);

    void removeFromSaveForLater(int userId, int articleId);
}
