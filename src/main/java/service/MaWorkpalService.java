package service;

import db.JdbcConnection;
import model.User;
import repository.interfaces.UserRepository;
import repository.implementation.*;
import service.implementation.AuthService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MaWorkpalService {
    private static MaWorkpalService instance;
    private final AuthService authService;
    private final Connection connection;

    private MaWorkpalService() throws SQLException {
        connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("error de connection"));
        UserRepository userRepository = new UserRepositoryImpl();
        authService = new AuthService(userRepository);
    }

    public static MaWorkpalService getInstance() throws SQLException {
        if (instance == null) {
            instance = new MaWorkpalService();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("************************************************************");
        System.out.println("                  Bonjour en Workpal !                       ");
        System.out.println("                                                            ");
        System.out.println("  Login                                     ");
        System.out.println("                                                            ");
        System.out.println("************************************************************");

        User user = authService.authenticate(scanner);
        if (user != null) {
            System.out.println("Login passe bien: " + user);
        }

        scanner.close();
        closeConnection();
    }
}
