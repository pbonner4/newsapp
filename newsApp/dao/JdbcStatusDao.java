package newsApp.dao;

import newsApp.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcStatusDao implements StatusDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void postStatus(int user_id, String username, String date, String Status, String media_file, String subject_tagged) {

        String sql = "INSERT INTO status_updates (user_id, username, date, status, media_file, subject_tagged) VALUES (?,?,?,?,?,?);";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, user_id, username, sqlTimestamp, Status, media_file, subject_tagged);

    }

    @Override
    public List<Status> getAllStatuses(){
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()){
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getUserStatus(int userId) {

        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates where user_id = ? ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while(rowSet.next()){
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;

    }

    @Override
    public void deleteStatus(int status_id, int user_id) {
        String sql = "DELETE from status_updates where status_id = ? AND user_id = ?;";

        jdbcTemplate.update(sql, status_id, user_id);
    }



    @Override
    public void likeAStatus(LikeStatus likeStatus, int userId) {

        String sql1 = "SELECT 1 FROM status_updates WHERE status_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, likeStatus.getStatus_id());

        String sql2 = "INSERT INTO liked_statuses (user_id, status_id, liked) VALUES (?,?,?);";
        jdbcTemplate.update(sql2, userId, likeStatus.getStatus_id(), likeStatus.isLiked());

    }

    @Override
    public void dislikeAStatus(DislikeStatus dislikeStatus, int userId) {

        String sql1 = "SELECT 1 FROM status_updates WHERE status_id = ?;";
        jdbcTemplate.queryForObject(sql1, Integer.class, dislikeStatus.getStatus_id());

        String sql2 = "INSERT INTO disliked_statuses (user_id, status_id, disliked) VALUES (?,?,?);";
        jdbcTemplate.update(sql2, userId, dislikeStatus.getStatus_id(), dislikeStatus.isDisliked());

    }

    @Override
    public List<LikeStatus> listLikedStatuses(int userId){
        List<LikeStatus> likes = new ArrayList<>();

        String sql = "SELECT * FROM liked_statuses WHERE user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            likes.add(mapRowFromLikedStatus(rowSet));
        }

        return likes;
    }
    @Override
    public List<DislikeStatus> listDislikedStatuses(int userId){
        List<DislikeStatus> dislikes = new ArrayList<>();

        String sql = "SELECT * FROM disliked_statuses WHERE user_id = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while (rowSet.next()) {
            dislikes.add(mapRowFromDislikedStatus(rowSet));
        }

        return dislikes;
    }

    @Override
    public void unlikeAStatus(int userId, int statusId) {
        String sql1 = "SELECT 1 FROM status_updates WHERE status_id = ?";
        jdbcTemplate.queryForObject(sql1, Integer.class, statusId);

        String sql2 = "DELETE FROM liked_statuses WHERE user_id = ? AND status_id = ?;";
        jdbcTemplate.update(sql2, userId, statusId);
    }

    @Override
    public void undislikeAStatus(int userId, int statusId) {
        String sql1 = "SELECT 1 FROM status_updates WHERE status_id = ?";
        jdbcTemplate.queryForObject(sql1, Integer.class, statusId);

        String sql2 = "DELETE FROM disliked_statuses WHERE user_id = ? AND status_id = ?;";
        jdbcTemplate.update(sql2, userId, statusId);
    }

    @Override
    public List<LikeCountStatuses> getLikeCountForStatuses(){
        List<LikeCountStatuses> count = new ArrayList<>();

        String sql = "SELECT status_id, COUNT(*) as count\n" +
                "FROM liked_statuses\n" +
                "GROUP BY status_id;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            count.add(mapRowFromLikeCountStatus(rowSet));
        }

        return count;

    }

    @Override
    public List<DislikeCountStatus> getDislikeCountForStatuses(){
        List<DislikeCountStatus> count = new ArrayList<>();

        String sql = "SELECT status_id, COUNT(*) as count\n" +
                "FROM disliked_statuses\n" +
                "GROUP BY status_id;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            count.add(mapRowFromDislikeCountStatus(rowSet));
        }

        return count;

    }

    @Override
    public List<Status> getFriendsStatuses(int userId){
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT s.*\n" +
                "FROM friends f\n" +
                "JOIN status_updates s ON (f.user_id_requestor = s.user_id OR f.user_id_requestee = s.user_id)\n" +
                "WHERE f.request_pending_or_accepted = true\n" +
                "AND (f.user_id_requestor = ? OR f.user_id_requestee = ?) ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, userId);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;

    }

    @Override
    public List<Status> getBusinessStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Business' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getNbaStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Nba' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getNhlStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Nhl' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getNflStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Nfl' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getMmaStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'MMA' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getPoliticsStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Politics' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getMoviesStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Movies' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getInTheatersStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'In Theaters' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getMusicStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Music' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> getTvStatuses() {
        List<Status> statuses = new ArrayList<>();

        String sql = "SELECT * FROM status_updates WHERE subject_tagged = 'Tv' ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }

    @Override
    public List<Status> listPeopleFollowingStatuses(String username) {

        List<Status> statuses = new ArrayList<>();

        System.out.println(username);

        String sql = "SELECT s.* FROM following f JOIN status_updates s ON s.user_id = f.user_followed_id WHERE f.user_following = ? ORDER BY date DESC;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);

        while (rowSet.next()) {
            statuses.add(mapRowFromStatus(rowSet));
        }

        return statuses;
    }


    private Status mapRowFromStatus(SqlRowSet rs){
        Status status = new Status();
        status.setId(rs.getInt("status_id"));
        status.setUser_id(rs.getInt("user_id"));
        status.setUsername(rs.getString("username"));
        status.setDate(rs.getString("date"));
        status.setStatus(rs.getString("status"));
        status.setMedia_file(rs.getString("media_file"));
        status.setSubject_tagged(rs.getString("subject_tagged"));
        return status;
    }

    private LikeStatus mapRowFromLikedStatus(SqlRowSet rs){

        LikeStatus status = new LikeStatus();
        status.setUser_id(rs.getInt("user_id"));
        status.setStatus_id(rs.getInt("status_id"));
        status.setLiked(rs.getBoolean("liked"));

        return  status;
    }

    private DislikeStatus mapRowFromDislikedStatus(SqlRowSet rs){

        DislikeStatus status = new DislikeStatus();
        status.setUser_id(rs.getInt("user_id"));
        status.setStatus_id(rs.getInt("status_id"));
        status.setDisliked(rs.getBoolean("disliked"));

        return  status;
    }

    private LikeCountStatuses mapRowFromLikeCountStatus(SqlRowSet rs){
        LikeCountStatuses count = new LikeCountStatuses();
        count.setStatus_id(rs.getInt("status_id"));
        count.setCount(rs.getInt("count"));
        return count;
    }

    private DislikeCountStatus mapRowFromDislikeCountStatus(SqlRowSet rs){
        DislikeCountStatus count = new DislikeCountStatus();
        count.setStatus_id(rs.getInt("status_id"));
        count.setCount(rs.getInt("count"));
        return count;
    }


}
