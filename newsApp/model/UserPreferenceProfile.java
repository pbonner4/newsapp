package newsApp.model;

public class UserPreferenceProfile {
    private int user_id;
    private boolean business;
    private boolean politics;
    private boolean right_wing;
    private boolean left_wing;
    private boolean center;
    private boolean sports;
    private boolean nfl;
    private boolean nba;
    private boolean nhl;
    private boolean mma;
    private boolean entertainment;
    private boolean movies;
    private boolean television;
    private boolean music;

    public UserPreferenceProfile(int user_id, boolean business, boolean politics, boolean right_wing, boolean left_wing, boolean center, boolean sports, boolean nfl, boolean nba, boolean nhl, boolean mma, boolean entertainment, boolean movies, boolean television, boolean music) {
        this.user_id = user_id;
        this.business = business;
        this.politics = politics;
        this.right_wing = right_wing;
        this.left_wing = left_wing;
        this.center = center;
        this.sports = sports;
        this.nfl = nfl;
        this.nba = nba;
        this.nhl = nhl;
        this.mma = mma;
        this.entertainment = entertainment;
        this.movies = movies;
        this.television = television;
        this.music = music;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public boolean isPolitics() {
        return politics;
    }

    public void setPolitics(boolean politics) {
        this.politics = politics;
    }

    public boolean isRight_wing() {
        return right_wing;
    }

    public void setRight_wing(boolean right_wing) {
        this.right_wing = right_wing;
    }

    public boolean isLeft_wing() {
        return left_wing;
    }

    public void setLeft_wing(boolean left_wing) {
        this.left_wing = left_wing;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public boolean isNfl() {
        return nfl;
    }

    public void setNfl(boolean nfl) {
        this.nfl = nfl;
    }

    public boolean isNba() {
        return nba;
    }

    public void setNba(boolean nba) {
        this.nba = nba;
    }

    public boolean isNhl() {
        return nhl;
    }

    public void setNhl(boolean nhl) {
        this.nhl = nhl;
    }

    public boolean isMma() {
        return mma;
    }

    public void setMma(boolean mma) {
        this.mma = mma;
    }

    public boolean isEntertainment() {
        return entertainment;
    }

    public void setEntertainment(boolean entertainment) {
        this.entertainment = entertainment;
    }

    public boolean isMovies() {
        return movies;
    }

    public void setMovies(boolean movies) {
        this.movies = movies;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }
}
