package newsApp.model;

public class SaveForLater {

    private int user_id;
    private int article_id;
    private boolean saveForLater;

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

    public boolean isSaveForLater() {
        return saveForLater;
    }

    public void setsaveForLater(boolean liked) {
        this.saveForLater = liked;
    }


}
