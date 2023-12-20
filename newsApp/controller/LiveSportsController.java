package newsApp.controller;

import newsApp.dao.JdbcLiveSportsDao;
import newsApp.model.Article;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class LiveSportsController {
    private JdbcLiveSportsDao liveSportsScores;

    public LiveSportsController(JdbcLiveSportsDao liveSportsScores) {
        this.liveSportsScores = liveSportsScores;
    }

    @CrossOrigin
    @RequestMapping( path ="/nbaScores", method = RequestMethod.GET)
    public String listNbaScores(){
        return liveSportsScores.getNbaScores();
    }

    @CrossOrigin
    @RequestMapping( path ="/nbaBoxScore/{boxScore}", method = RequestMethod.GET)
    public String listNbaBoxScore(@PathVariable String boxScore){
        boxScore = boxScore.replaceAll("\\{", "").replaceAll("\\}", "");
        return liveSportsScores.getNbaBoxScores(boxScore);
    }

    @CrossOrigin
    @RequestMapping( path ="/nhlScores", method = RequestMethod.GET)
    public String listNhlScores(){
        return liveSportsScores.getNhlScores();
    }

    @CrossOrigin
    @RequestMapping( path ="/mmaScores", method = RequestMethod.GET)
    public String listMmaScores(){
        return liveSportsScores.getMmaScores();
    }
    @CrossOrigin
    @RequestMapping( path ="/nflScores", method = RequestMethod.GET)
    public String listNflScores(){
        return liveSportsScores.getNflScores();
    }
}
