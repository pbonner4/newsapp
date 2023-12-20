package newsApp.dao;

import newsApp.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcFriendRequestDao implements FriendRequestDao{

    private final JdbcTemplate jdbcTemplate;

    JdbcFriendRequestDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void sendFriendRequest(FriendRequest friendRequest) {

        String sql = "INSERT INTO friends (user_id_requestor, username_requestor, user_id_requestee, username_requestee, request_pending_or_accepted) VALUES (?,?,?,?,?);";

        jdbcTemplate.update(sql, friendRequest.getUserIdReqestor(), friendRequest.getUsernameRequestor(), friendRequest.getUserIdRequestee(), friendRequest.getUsernameRequestee(), friendRequest.isRequest_pending_or_accepted());

    }

    @Override
    public void deleteFriendRequest(int requesteeUserId, int requestorUserId) {

        String sql = "DELETE FROM friends WHERE user_id_requestor = ? AND user_id_requestee = ? AND request_pending_or_accepted = false;";

        jdbcTemplate.update(sql, requestorUserId, requesteeUserId);

    }

    @Override
    public List<FriendRequest> retrieveFriendRequests(int userId) {
        List<FriendRequest> friendRequests= new ArrayList<>();

        String sql = "SELECT * FROM friends WHERE user_id_requestee = ? AND request_pending_or_accepted = false;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            friendRequests.add(mapRowFromFriendRequest(rowSet));
        }

        return friendRequests;

    }

    @Override
    public List<FriendRequest> retrievePendingFriendRequests(int userId) {
        List<FriendRequest> friendRequests= new ArrayList<>();

        String sql = "SELECT * FROM friends WHERE user_id_requestor = ? AND request_pending_or_accepted = false;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            friendRequests.add(mapRowFromFriendRequest(rowSet));
        }

        return friendRequests;

    }

    @Override
    public List<FriendRequest> retrieveFriends(int userId) {
        List<FriendRequest> friendRequests= new ArrayList<>();

        String sql = "SELECT * FROM friends WHERE user_id_requestee = ? and request_pending_or_accepted = true;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            friendRequests.add(mapRowFromFriendRequest(rowSet));
        }

        return friendRequests;

    }

    @Override
    public void followSomeone(Following following) {
        String sql = "INSERT INTO following (user_following, user_id_following,user_followed,user_followed_id) VALUES (?,?,?,?);";

        jdbcTemplate.update(sql, following.getUser_following(), following.getUser_id_following(), following.getUser_followed(), following.getUser_followed_id());
    }

    @Override
    public void unfollowSomeone(int userId, int followedUserId) {
        String sql = "DELETE FROM following where user_id_following = ? AND user_followed_id = ?;";

        jdbcTemplate.update(sql, userId, followedUserId);
    }

    @Override
    public List<Following> listPeopleFollowing(int userId) {

        List<Following> following = new ArrayList<>();

        String sql = "SELECT * FROM following WHERE user_id_following = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            following.add(mapRowFromFollowing(rowSet));
        }

        return following;
    }

    @Override
    public void acceptFriendRequest(int requesteeUserId, int requestorUserId) {

        String sql = "UPDATE friends SET request_pending_or_accepted = true WHERE user_id_requestor = ? AND user_id_requestee = ?;";


        jdbcTemplate.update(sql, requestorUserId, requesteeUserId);

    }





    private FriendRequest mapRowFromFriendRequest(SqlRowSet rs){
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setRequest_id(rs.getInt("request_id"));
        friendRequest.setUserIdReqestor(rs.getInt("user_id_requestor"));
        friendRequest.setUsernameRequestor(rs.getString("username_requestor"));
        friendRequest.setUserIdRequestee(rs.getInt("user_id_requestee"));
        friendRequest.setUsernameRequestee(rs.getString("username_requestee"));
        friendRequest.setRequest_pending_or_accepted(rs.getBoolean("request_pending_or_accepted"));

        return friendRequest;

    }

    private Following mapRowFromFollowing(SqlRowSet rs){
        Following following = new Following();

        following.setUser_following(rs.getString("user_following"));
        following.setUser_id_following(rs.getInt("user_id_following"));
        following.setUser_followed(rs.getString("user_followed"));
        following.setUser_followed_id(rs.getInt("user_followed_id"));

        return following;
    }


}
