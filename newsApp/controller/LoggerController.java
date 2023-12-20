package newsApp.controller;

import newsApp.dao.JdbcArticleDao;
import newsApp.dao.JdbcLoggerDao;
import newsApp.dao.JdbcUserDao;
import newsApp.dao.UserDao;
import newsApp.model.Collector;
import newsApp.model.articleTemplate.Result;
import newsApp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class LoggerController {
    private JdbcLoggerDao loggerDao;
    private UserDao userDao;

    public LoggerController(JdbcLoggerDao loggerDao, JdbcUserDao userDao) {
        this.loggerDao = loggerDao;
        this.userDao = userDao;
    }

    @CrossOrigin
    @RequestMapping(path = "/collect/log", method = RequestMethod.POST)
    public ResponseEntity<String> insertUserEvent(@RequestBody Collector collect, Principal principal) {
        String signedInUser = principal.getName();
        int userId = userDao.findIdByUsername(signedInUser);
        loggerDao.insertUserEvent(collect.getCreated(), userId, collect.getArticleId(), collect.getEvent(), collect.getSessionId());
        return new ResponseEntity<>("Event added", HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(path = "/crsf", method = RequestMethod.GET)
    public String getCRSF(CsrfToken csrf){
        String crsfToken = csrf.getToken();
        return crsfToken;
    }

}
