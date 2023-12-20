package newsApp.dao;

import java.util.Date;

public interface LoggerDao {
    void insertUserEvent(Date created, int userId, String articleId, String event, String sessionId);

}
