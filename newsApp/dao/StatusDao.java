package newsApp.dao;

import newsApp.model.*;

import java.util.List;

public interface StatusDao {

    void postStatus(int user_id, String username, String date, String Status, String media_file, String subject_tagged);
    public List<Status> getUserStatus(int userId);
    public List<Status> getAllStatuses();
    void deleteStatus(int status_id, int user_id);

    void likeAStatus(LikeStatus likeStatus, int userId);
    void dislikeAStatus(DislikeStatus dislikeStatus, int userId);

    public List<LikeStatus> listLikedStatuses(int userId);
    public List<DislikeStatus> listDislikedStatuses(int userId);

    void unlikeAStatus(int userId, int articleId);
    public void undislikeAStatus(int userId, int statusId);
    public List<LikeCountStatuses> getLikeCountForStatuses();
    public List<DislikeCountStatus> getDislikeCountForStatuses();

    List<Status> listPeopleFollowingStatuses(String username);

    public List<Status> getFriendsStatuses(int userId);

    public List<Status> getBusinessStatuses();
    public List<Status> getNbaStatuses();
    public List<Status> getNhlStatuses();
    public List<Status> getNflStatuses();
    public List<Status> getMmaStatuses();
    public List<Status> getPoliticsStatuses();
    public List<Status> getMoviesStatuses();
    public List<Status> getInTheatersStatuses();
    public List<Status> getMusicStatuses();
    public List<Status> getTvStatuses();



}
