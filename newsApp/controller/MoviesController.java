package newsApp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import newsApp.dao.JdbcMovieDao;
import newsApp.model.Article;
import newsApp.model.Movie;
import newsApp.model.articleTemplate.MovieData;
import newsApp.model.articleTemplate.Movies;

import newsApp.model.articleTemplate.Result;
import newsApp.services.MoviesService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class MoviesController {
    @Autowired
    private MoviesService moviesService;

    private JdbcMovieDao movieDao;

    public MoviesController(MoviesService moviesService, JdbcMovieDao movieDao) {
        this.movieDao = movieDao;
        this.moviesService = moviesService;
    }
    @CrossOrigin
    @PreAuthorize("permitAll()")
    @RequestMapping( path ="/nowPlaying", method = RequestMethod.GET)
    public List<Movie> listNowPlaying(){
        return movieDao.getNowPlayingMovies();
    }

    @Scheduled(cron = "0 0 */15 * * *")
    @JsonProperty
    @PostMapping(path = "/nowPlaying")
    public ResponseEntity<String> insertNowPlaying() {

        movieDao.deleteNowPlayingMovies();

        List<Movies> movies = moviesService.getNowPlaying();

        if (movies != null) {
            return ResponseEntity.ok("Movies inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting movies");
        }
    }

    @JsonProperty
    @CrossOrigin
    @RequestMapping(path = "/comingSoon", method = RequestMethod.GET)
    public List<Movie> getComingSoon() {
        return movieDao.getComingSoon();
    }

    @Scheduled(cron = "0 0 */14 * * *")
    @JsonProperty
    @PostMapping(path = "/comingSoon")
    public ResponseEntity<String> insertComingSoon() {

        movieDao.deleteComingSoonMovies();

        List<Movies> movies = moviesService.getComingSoon();

        if (movies != null) {
            return ResponseEntity.ok("Movies inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting movies");
        }
    }

}
