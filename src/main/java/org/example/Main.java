package org.example;

import utils.database.JdbcConnection;
import java.sql.Connection;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<Connection> conn = JdbcConnection.getConnection();

        if (conn.isPresent()) {
            System.out.println("Connection is available.");
        } else {
            System.out.println("Failed to establish a connection.");
        }

        System.out.println(conn);
    }
}
