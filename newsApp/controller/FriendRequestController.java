package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.JdbcCommodityDataDao;
import newsApp.dao.JdbcFriendRequestDao;
import newsApp.dao.JdbcUserDao;
import newsApp.model.Following;
import newsApp.model.FriendRequest;
import newsApp.model.PercentageChange;
import newsApp.model.User;
import newsApp.model.articleTemplate.Commodity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class FriendRequestController {

    private JdbcFriendRequestDao jdbcFriendRequestDao;
    private JdbcUserDao userDao;
    public FriendRequestController(JdbcFriendRequestDao jdbcFriendRequestDao, JdbcUserDao userDao) {
        this.jdbcFriendRequestDao = jdbcFriendRequestDao;
        this.userDao = userDao;
    }

    @CrossOrigin
    @RequestMapping(path = "/sendFriendRequest", method = RequestMethod.POST)
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) {

        jdbcFriendRequestDao.sendFriendRequest(friendRequest);

        if (friendRequest != null) {
            return ResponseEntity.ok("request sent");
        } else {
            return ResponseEntity.badRequest().body("request could not be sent please make sure the friend request has all required attributes");
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/followSomeone", method = RequestMethod.POST)
    public ResponseEntity<String> followSomeone(@RequestBody Following following) {

        jdbcFriendRequestDao.followSomeone(following);

        if (following != null) {
            return ResponseEntity.ok("request sent");
        } else {
            return ResponseEntity.badRequest().body("request could not be sent please make sure the follow request has all required attributes");
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/followSomeone/{userFollowedId}", method = RequestMethod.POST)
    public ResponseEntity<String> unfollowSomeone(Principal principal, @PathVariable int userFollowedId) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        jdbcFriendRequestDao.unfollowSomeone(userId, userFollowedId);

        return new ResponseEntity<>("unfollowed", HttpStatus.OK);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/deleteFriendRequest/{requestee_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStatus(@PathVariable(name = "requestee_id", required = false) int requestee_id, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        jdbcFriendRequestDao.deleteFriendRequest(requestee_id, userId);

        return new ResponseEntity<>("Status deleted", HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(path = "/acceptFriendRequest/{userRequestorId}", method = RequestMethod.PUT)
    public ResponseEntity<String> acceptFriendRequest(Principal principal, @PathVariable Integer userRequestorId) {

        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);

        jdbcFriendRequestDao.acceptFriendRequest(userId, userRequestorId);

        if (userId != 0 && userRequestorId != 0) {
            return ResponseEntity.ok("request accepted");
        } else {
            return ResponseEntity.badRequest().body("request could not be accepted please make sure the friend request has all required attributes");
        }

    }

    @CrossOrigin
    @RequestMapping(path = "/friendRequests", method = RequestMethod.GET)
    public List<FriendRequest> listFriendRequests(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return jdbcFriendRequestDao.retrieveFriendRequests(userId);
    }
    @CrossOrigin
    @RequestMapping(path = "/pendingFriendRequests", method = RequestMethod.GET)
    public List<FriendRequest> listPendingRequests(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return jdbcFriendRequestDao.retrievePendingFriendRequests(userId);
    }

    @CrossOrigin
    @RequestMapping(path = "/friends", method = RequestMethod.GET)
    public List<FriendRequest> listFriends(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return jdbcFriendRequestDao.retrieveFriends(userId);
    }

    @CrossOrigin
    @RequestMapping(path = "/getAllUsers", method = RequestMethod.GET)
    public List<User> listUsers(Principal principal){
        String signedInUser = principal.getName();
        return userDao.findAll();
    }

    @CrossOrigin
    @RequestMapping(path = "/getPeopleFollowing", method = RequestMethod.GET)
    public List<Following> getPeopleFollowing(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return jdbcFriendRequestDao.listPeopleFollowing(userId);
    }
}
