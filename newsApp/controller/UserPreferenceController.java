package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.*;
import newsApp.model.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class UserPreferenceController {


    private JdbcUserPreferenceDao profileDao;
    private UserDao userDao;

    public UserPreferenceController(JdbcUserPreferenceDao profile, JdbcUserDao userDao) {
        this.profileDao = profile;
        this.userDao = userDao;
    }


    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/build", method = RequestMethod.POST)
    public void createProfile(@RequestBody UserPreferenceProfile userPreferenceProfile, Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.buildUserPreferenceProfile(userPreferenceProfile, userId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/update", method = RequestMethod.POST)
    public void updateProfile(@RequestBody UserPreferenceProfile userPreferenceProfile, Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.updatePreferenceProfile(userPreferenceProfile, userId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/likePost/{articleId}", method = RequestMethod.POST)
    public void likePost(@RequestBody Like like, Principal principal, @PathVariable int articleId) {
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.likeAPost(like, userId, articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/unlikePost/{articleId}", method = RequestMethod.POST)
    public void unlikePost(Principal principal, @PathVariable int articleId){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.unlikeAPost(userId, articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/dislikePost/{articleId}", method = RequestMethod.POST)
    public void dislikePost(@RequestBody Dislike like, Principal principal, @PathVariable int articleId){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.dislikeAPost(like, userId, articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/undislikePost/{articleId}", method = RequestMethod.POST)
    public void undislikePost(Principal principal, @PathVariable int articleId){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.undislikeAPost(userId, articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/saveForLater/{articleId}", method = RequestMethod.POST)
    public void saveForLater(@RequestBody SaveForLater later, Principal principal, @PathVariable int articleId){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.saveForLater(later, userId, articleId);
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/removeFromSaveForLater/{articleId}", method = RequestMethod.POST)
    public void removeFromSaveForLater(Principal principal, @PathVariable int articleId){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        profileDao.removeFromSaveForLater(userId, articleId);
    }

    @PreAuthorize("permitAll")
    @CrossOrigin
    @RequestMapping(path = "/whoami", method = RequestMethod.GET)
    public int whoAmI(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return userId;
    }

    @PreAuthorize("permitAll")
    @CrossOrigin
    @RequestMapping(path = "/whoami/name", method = RequestMethod.GET)
    public String whoAmIname(Principal principal){
        String signedInUser = principal.getName();
        return signedInUser;
    }

    @CrossOrigin
    @RequestMapping(path = "/userpreferences/likePostCheck", method = RequestMethod.GET)
    public List<Article> checklikePost(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return profileDao.hasLikedAPostCheck(userId);
    }
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/dislikes", method = RequestMethod.GET)
    public List<Article> listDislikes(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return profileDao.listDislikes(userId);
    }
    @CrossOrigin
    @RequestMapping(path = "/userpreferences/saveForLaters", method = RequestMethod.GET)
    public List<Article> listSaveForlaters(Principal principal){
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        return profileDao.listSaveForLater(userId);
    }
}
