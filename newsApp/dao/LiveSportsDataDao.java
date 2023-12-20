package newsApp.dao;

public interface LiveSportsDataDao {

     String getNbaScores();

     String getNhlScores();
     String getMmaScores();
     String getNflScores();

     String getNbaBoxScores(String eventId);
}
