package repository.implementation;

import db.JdbcConnection;
import model.User;
import Enum.*;
import repository.interfaces.UserRepository;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("error de connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("error de connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?) ON CONFLICT (email) DO UPDATE SET name = EXCLUDED.name, password = EXCLUDED.password, role = EXCLUDED.role";
        try (Connection connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("error connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole().name().toLowerCase());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        UserRole role = UserRole.valueOf(rs.getString("role"));
        return new User(id, name, email, password, role);
    }
}
