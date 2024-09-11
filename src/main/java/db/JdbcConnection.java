package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcConnection {

    private static Optional<Connection> connection = Optional.empty();

    public static Optional<Connection> getConnection() {
        if (connection.isEmpty()) {
            String url = "jdbc:postgresql://localhost:5432/WorkPal";
            String user = "postgres";
            String password = "admin";

            try {
                connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
                System.out.println("Connection established successfully.");
            } catch (SQLException ex) {
                System.out.println("Error connecting to the database: " + ex.getMessage());
            }
        }

        return connection;
    }
}
