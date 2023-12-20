package newsApp.dao;

import newsApp.model.ArticleContent;

import java.util.List;

public interface ArticleContentDao {

    void insertBusinessContent();
    void insertNbaContent();

    void insertNflContent();
    void insertNhlContent();

    void insertMmaContent();
    void insertMovieContent();
    void insertTvContent();
    void insertMusicContent();

    void insertCenterContent();
    void insertLeftContent();
    void insertRightContent();


}
