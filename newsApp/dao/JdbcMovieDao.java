package newsApp.dao;

import newsApp.model.Article;
import newsApp.model.ArticleContent;
import newsApp.model.Movie;
import newsApp.model.Trending;
import newsApp.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
@Repository
public class JdbcMovieDao implements MoviesDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void insertNowPlaying(Integer id, String originalLanguage, String original_title, String overview, int popularity, String poster_path, String release_date, String title, Boolean video, double voteAverage, int voteCount, String trailer) {
        String sql = "INSERT INTO now_playing (id, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count, trailer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(release_date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, id, originalLanguage, original_title, overview, popularity, poster_path, sqlTimestamp, title, video, voteAverage, voteCount, trailer);

    }

    @Override
    public void insertComingSoon(Integer id, String originalLanguage, String original_title, String overview, int popularity, String poster_path, String release_date, String title, Boolean video, double voteAverage, int voteCount, String trailer) {
        String sql = "INSERT INTO coming_soon (id, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count, trailer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?) ON CONFLICT (id) DO NOTHING;";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(release_date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, id, originalLanguage, original_title, overview, popularity, poster_path, sqlTimestamp, title, video, voteAverage, voteCount, trailer);

    }

    @Override
    public void deleteNowPlayingMovies(){
            String sqlDelete = "DELETE FROM now_playing;";
            jdbcTemplate.update(sqlDelete);
    }

    @Override
    public void deleteComingSoonMovies(){
        String sqlDelete = "DELETE FROM coming_soon;";
        jdbcTemplate.update(sqlDelete);
    }


    @Override
    public List<Movie> getNowPlayingMovies(){
        List<Movie> movies = new ArrayList<>();

        String sql = "select * from now_playing ORDER BY popularity DESC";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            movies.add(mapRowFromMovies(rowSet));
        }

        return movies;
    }

    @Override
    public List<Movie> getComingSoon(){
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM coming_soon ORDER BY popularity DESC";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            movies.add(mapRowFromMovies(rowSet));
        }

        return movies;
    }

    private Movie mapRowFromMovies(SqlRowSet rs){
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setOrginal_language(rs.getString("original_language"));
        movie.setOriginal_title(rs.getString("original_title"));
        movie.setOverview(rs.getString("overview"));
        movie.setPopularity(rs.getInt("popularity"));
        movie.setPoster_path(rs.getString("poster_path"));
        movie.setRealease_date(rs.getString("release_date"));
        movie.setTitle(rs.getString("title"));
        movie.setVideo(rs.getBoolean("video"));
        movie.setVote_average(rs.getInt("vote_average"));
        movie.setVote_count(rs.getInt("vote_count"));
        movie.setTrailer(rs.getString("trailer"));

        return movie;
    }
}


