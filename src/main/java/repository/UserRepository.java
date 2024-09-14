//package repository;
//
//import model.User;
//import java.sql.*;
//import java.util.Optional;
//import java.util.UUID;
//
//public class UserRepository {
//    private Connection connection;
//
//    public UserRepository(Connection connection) {
//        this.connection = connection;
//    }
//
//    // Enregistrer un nouvel utilisateur
//    public boolean registerUser(User user) {
//        String query = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setObject(1, UUID.fromString(user.getId()));
//            ps.setString(2, user.getName());
//            ps.setString(3, user.getEmail());
//            ps.setString(4, user.getPassword());
//            ps.setString(5, user.getRole());
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Connexion d'un utilisateur
//    public Optional<User> loginUser(String email, String password) {
//        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, email);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                String id = rs.getString("id");
//                String name = rs.getString("name");
//                String role = rs.getString("role");
//                User user = new User(id, name, email, password, role);
//                return Optional.of(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    // Trouver un utilisateur par email
//    public Optional<User> findUserByEmail(String email) {
//        String query = "SELECT * FROM users WHERE email = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                String id = rs.getString("id");
//                String name = rs.getString("name");
//                String password = rs.getString("password");
//                String role = rs.getString("role");
//                User user = new User(id, name, email, password, role);
//                return Optional.of(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    // Supprimer un utilisateur
//    public boolean deleteUser(String email) {
//        String query = "DELETE FROM users WHERE email = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, email);
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//}
