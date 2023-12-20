package newsApp.model;

import java.util.Date;

public class UserRating {
    private String user_id;
    private String article_id;
    private Double rating;
    private Date rating_timestamp;
    private String type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getRating_timestamp() {
        return rating_timestamp;
    }

    public void setRating_timestamp(Date rating_timestamp) {
        this.rating_timestamp = rating_timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
