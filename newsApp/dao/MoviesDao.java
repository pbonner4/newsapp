package newsApp.dao;

import newsApp.model.Movie;
import java.util.List;

public interface MoviesDao {

    void insertNowPlaying(Integer id, String originalLanguage, String original_title, String overview, int popularity, String poster_path, String release_date, String title, Boolean video, double voteAverage, int voteCount, String trailer);
    void insertComingSoon(Integer id, String originalLanguage, String original_title, String overview, int popularity, String poster_path, String release_date, String title, Boolean video, double voteAverage, int voteCount, String trailer);
    public List<Movie> getNowPlayingMovies();
    public List<Movie> getComingSoon();

    void deleteNowPlayingMovies();

    void deleteComingSoonMovies();

}
