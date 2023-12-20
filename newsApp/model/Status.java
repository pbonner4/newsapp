package newsApp.model;

public class Status {

    private int id;
    private int user_id;
    private String username;
    private String date;
    private String status;
    private String media_file;
    private String subject_tagged;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMedia_file() {
        return media_file;
    }

    public void setMedia_file(String media_file) {
        this.media_file = media_file;
    }

    public String getSubject_tagged() {
        return subject_tagged;
    }

    public void setSubject_tagged(String subject_tagged) {
        this.subject_tagged = subject_tagged;
    }
}
