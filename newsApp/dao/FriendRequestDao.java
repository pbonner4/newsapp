package newsApp.dao;

import newsApp.model.Following;
import newsApp.model.FriendRequest;
import newsApp.model.Status;

import java.util.List;

public interface FriendRequestDao {
    void sendFriendRequest(FriendRequest friendRequest);
    void deleteFriendRequest(int requesteeUserId, int requestorUserId);
    List<FriendRequest> retrieveFriendRequests(int userId);
    List<FriendRequest> retrievePendingFriendRequests(int userId);
    void acceptFriendRequest(int requesteeUserId, int requestorUserId);

    public List<FriendRequest> retrieveFriends(int userId);

    void followSomeone(Following following);

    public List<Following> listPeopleFollowing(int userId);

    void unfollowSomeone(int userId, int followedUserId);

}
