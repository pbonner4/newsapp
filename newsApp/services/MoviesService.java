package newsApp.services;

import newsApp.dao.JdbcArticleDao;
import newsApp.dao.JdbcMovieDao;
import newsApp.model.MovieTrailer;
import newsApp.model.articleTemplate.MovieData;
import newsApp.model.articleTemplate.MovieTrailerData;
import newsApp.model.articleTemplate.Movies;
import newsApp.model.articleTemplate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MoviesService {
    private RestTemplate restTemplate = new RestTemplate();

    private final JdbcMovieDao movieDao;

    @Autowired
    public MoviesService(JdbcMovieDao movieDao) {
        this.movieDao = movieDao;
    }
    public static String MOVIE_API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    @Value("${movieApiKey}")
    private String movieApiKey;
    public String MOVIE_API_BASE_KEY;

    @PostConstruct
    private void initializeMovieApiKey() {
        MOVIE_API_BASE_KEY = "?apikey=" + movieApiKey;
    }

    public List<Movies> getNowPlaying() {
        MovieData movieData = restTemplate.getForObject(MOVIE_API_BASE_URL + "now_playing" + MOVIE_API_BASE_KEY + "&region=US", MovieData.class);
        assert movieData != null;


        List<Movies> movies = movieData.getResults();

        for (Movies movie : movies) {
            MovieTrailerData movieTrailerData = restTemplate.getForObject(MOVIE_API_BASE_URL + movie.getId() + "/videos" + MOVIE_API_BASE_KEY + "&language=en-US", MovieTrailerData.class);
            List<MovieTrailer> trailers = movieTrailerData.getVideos();
            String youtubeKey = null;
            String youtubeUrl = "https://www.youtube.com/embed/";
            for (MovieTrailer trailer : trailers){
                if(trailer.getName().equals("Official Trailer")){
                    System.out.println(trailer.getKey());
                    youtubeKey = trailer.getKey();
                    System.out.println(youtubeKey);
                }
            }
            String youtubeVideo= youtubeUrl + youtubeKey;
            movieDao.insertNowPlaying(movie.getId(), movie.getOriginal_language(), movie.getOriginal_title(), movie.getOverview(), movie.getPopularity(), movie.getPoster_path(), movie.getRelease_date(), movie.getTitle(), movie.getVideo(), movie.getVote_average(), movie.getVote_count(), youtubeVideo);
        }

        return movies;
    }

    public List<Movies> getComingSoon() {

        MovieData movieData = restTemplate.getForObject(MOVIE_API_BASE_URL + "upcoming" + MOVIE_API_BASE_KEY + "&region=US", MovieData.class);
        assert movieData != null;


        List<Movies> movies = movieData.getResults();

        for (Movies movie : movies) {
            MovieTrailerData movieTrailerData = restTemplate.getForObject(MOVIE_API_BASE_URL + movie.getId() + "/videos" + MOVIE_API_BASE_KEY + "&language=en-US", MovieTrailerData.class);
            List<MovieTrailer> trailers = movieTrailerData.getVideos();
            String youtubeKey = null;
            String youtubeUrl = "https://www.youtube.com/embed/";
            for (MovieTrailer trailer : trailers){
                if(trailer.getName().equals("Official Trailer")){
                    System.out.println(trailer.getKey());
                    youtubeKey = trailer.getKey();
                    System.out.println(youtubeKey);
                }
            }
            String youtubeVideo= youtubeUrl + youtubeKey;
            movieDao.insertComingSoon(movie.getId(), movie.getOriginal_language(), movie.getOriginal_title(), movie.getOverview(), movie.getPopularity(), movie.getPoster_path(), movie.getRelease_date(), movie.getTitle(), movie.getVideo(), movie.getVote_average(), movie.getVote_count(), youtubeVideo);
        }

        return movies;
    }

}
