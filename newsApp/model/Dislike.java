package newsApp.model;

public class Dislike {

    private int user_id;
    private int article_id;
    private boolean disliked;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean liked) {
        this.disliked = liked;
    }
}
