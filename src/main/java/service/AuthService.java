
package service;

import repository.UserRepository;
import model.User;
import java.sql.Connection;
import java.util.Optional;
import java.util.UUID;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(Connection connection) {
        this.userRepository = new UserRepository(connection);
    }


    public boolean registerUser(String name, String email, String password, String role) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, name, email, password, role);
        return userRepository.registerUser(user);
    }

    public Optional<User> loginUser(String email, String password) {
        return userRepository.loginUser(email, password);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean deleteUser(String email) {
        return userRepository.deleteUser(email);
    }
}
