package newsApp.model;

public class Following {
    private String user_following;
    private int user_id_following;
    private String user_followed;
    private int user_followed_id;

    public String getUser_following() {
        return user_following;
    }

    public void setUser_following(String user_following) {
        this.user_following = user_following;
    }

    public int getUser_id_following() {
        return user_id_following;
    }

    public void setUser_id_following(int user_id_following) {
        this.user_id_following = user_id_following;
    }

    public String getUser_followed() {
        return user_followed;
    }

    public void setUser_followed(String user_followed) {
        this.user_followed = user_followed;
    }

    public int getUser_followed_id() {
        return user_followed_id;
    }

    public void setUser_followed_id(int user_followed_id) {
        this.user_followed_id = user_followed_id;
    }
}
