package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.JdbcStatusDao;
import newsApp.dao.JdbcUserDao;
import newsApp.model.*;
import newsApp.model.articleTemplate.Movies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class StatusController {
    private JdbcStatusDao statusDao;
    private JdbcUserDao userDao;

    StatusController(JdbcStatusDao statusDao, JdbcUserDao userDao){
        this.statusDao = statusDao;
        this.userDao = userDao;
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/status", method = RequestMethod.POST)
    public ResponseEntity<String> postStatus(@RequestBody Status status, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.postStatus(userId, username, status.getDate(), status.getStatus(), status.getMedia_file(), status.getSubject_tagged());

        return new ResponseEntity<>("Status added", HttpStatus.OK);

    }

    @JsonProperty
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin
    @RequestMapping(path = "/status/{status_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStatus(@PathVariable int status_id, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.deleteStatus(status_id, userId);

        return new ResponseEntity<>("Status deleted", HttpStatus.OK);

    }

    @JsonProperty
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin
    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public List<Status> getUserStatuses(Principal principal) {

        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);

        return statusDao.getUserStatus(userId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/allStatuses", method = RequestMethod.GET)
    public List<Status> getAllStatuses() {
        return statusDao.getAllStatuses();
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/statusLikeCount", method = RequestMethod.GET)
    public List<LikeCountStatuses> getStatusLikeCount() {

        return statusDao.getLikeCountForStatuses();

    }

    @JsonProperty
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin
    @RequestMapping(path = "/peopleFollowingStatuses", method = RequestMethod.GET)
    public List<Status> getPeopleFollowingStatuses(Principal principal) {

        String signedInUser = principal.getName();

        return statusDao.listPeopleFollowingStatuses(signedInUser);

    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/statusDislikeCount", method = RequestMethod.GET)
    public List<DislikeCountStatus> getStatusDislikeCount() {

        return statusDao.getDislikeCountForStatuses();

    }

    @CrossOrigin
    @RequestMapping(path = "/friendsStatuses", method = RequestMethod.GET)
    public List<Status> listfriendsStatuses(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return statusDao.getFriendsStatuses(userId);
    }

    @CrossOrigin
    @RequestMapping(path = "/listLikedStatuses", method = RequestMethod.GET)
    public List<LikeStatus> listLikedStats(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return statusDao.listLikedStatuses(userId);
    }

    @CrossOrigin
    @RequestMapping(path = "/listDislikedStatuses", method = RequestMethod.GET)
    public List<DislikeStatus> listDislikedStats(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return statusDao.listDislikedStatuses(userId);
    }

    @CrossOrigin
    @RequestMapping(path = "/likeStatus", method = RequestMethod.POST)
    public ResponseEntity<String> likeAStatus(@RequestBody LikeStatus likeStatus, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.likeAStatus(likeStatus, userId);

        return new ResponseEntity<>("Status liked", HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(path = "/dislikeStatus", method = RequestMethod.POST)
    public ResponseEntity<String> dislikeAStatus(@RequestBody DislikeStatus dislikeStatus, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.dislikeAStatus(dislikeStatus, userId);

        return new ResponseEntity<>("Status disliked", HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(path = "/unlikeAStatus/{status_id}", method = RequestMethod.POST)
    public ResponseEntity<String> unlikeAStatus(@PathVariable int status_id, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.unlikeAStatus(userId, status_id);

        return new ResponseEntity<>("Status unliked", HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(path = "/undislikeAStatus/{status_id}", method = RequestMethod.POST)
    public ResponseEntity<String> undislikeAStatus(@PathVariable int status_id, Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        statusDao.undislikeAStatus(userId, status_id);

        return new ResponseEntity<>("Status disliked", HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(path = "/businessStatuses", method = RequestMethod.GET)
    public List<Status> listBusinessStatuses(){
        return statusDao.getBusinessStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/nbaStatuses", method = RequestMethod.GET)
    public List<Status> listNbaStatuses(){
        return statusDao.getNbaStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/nflStatuses", method = RequestMethod.GET)
    public List<Status> listNflStatuses(){
        return statusDao.getNflStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/nhlStatuses", method = RequestMethod.GET)
    public List<Status> listNhlStatuses(){
        return statusDao.getNhlStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/mmaStatuses", method = RequestMethod.GET)
    public List<Status> listMmaStatuses(){
        return statusDao.getMmaStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/politicsStatuses", method = RequestMethod.GET)
    public List<Status> listPoliticsStatuses(){
        return statusDao.getPoliticsStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/moviesStatuses", method = RequestMethod.GET)
    public List<Status> listMovieStatuses(){
        return statusDao.getMoviesStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/inTheatersStatuses", method = RequestMethod.GET)
    public List<Status> listInTheatersStatuses(){
        return statusDao.getInTheatersStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/musicStatuses", method = RequestMethod.GET)
    public List<Status> listMusicStatuses(){

        return statusDao.getMusicStatuses();
    }
    @CrossOrigin
    @RequestMapping(path = "/tvStatuses", method = RequestMethod.GET)
    public List<Status> listTvStatuses(){
        return statusDao.getTvStatuses();
    }




}
