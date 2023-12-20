package newsApp.dao;

import newsApp.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role, String email);

    void createGoogleUser(String username, String password, String role, String email, String firstName, String lastName);
}
