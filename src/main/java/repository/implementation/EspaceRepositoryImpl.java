package repository.implementation;

import db.JdbcConnection;
import model.Espace;
import repository.interfaces.EspaceRepository;

import java.sql.*;
import java.util.*;

public class EspaceRepositoryImpl implements EspaceRepository {
    @Override
    public Espace ajouterEspace(Espace espace) throws SQLException {
        String sql = "INSERT INTO espace (id, nom, type, capacite, equipements, description, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, UUID.randomUUID());
            stmt.setString(2, espace.getNom());
            stmt.setString(3, espace.getType().name());
            stmt.setInt(4, espace.getCapacite());
            stmt.setArray(5, connection.createArrayOf("TEXT", espace.getEquipements().toArray(new String[0])));
            stmt.setString(6, espace.getDescription());
            stmt.setBigDecimal(7, espace.getPrix());

            if (stmt.executeUpdate() > 0) {
                return espace;
            }
        }
        return null;
    }

    @Override
    public Espace modifierEspace(Espace espace) throws SQLException {
        String sql = "UPDATE espace SET nom = ?, type = ?, capacite = ?, equipements = ?, description = ?, prix = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, espace.getNom());
            stmt.setString(2, espace.getType().name());
            stmt.setInt(3, espace.getCapacite());
            stmt.setArray(4, connection.createArrayOf("TEXT", espace.getEquipements().toArray(new String[0])));
            stmt.setString(5, espace.getDescription());
            stmt.setBigDecimal(6, espace.getPrix());
            stmt.setObject(7, espace.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return espace;
            }
        }
        return null;
    }

    @Override
    public void supprimerEspace(UUID id) throws SQLException {
        String sql = "DELETE FROM espace WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }
}
