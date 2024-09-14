package repository.implementation;

import db.JdbcConnection;
import model.Espace;
import Enum.*;
import repository.interfaces.EspaceRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class EspaceRepositoryImpl implements EspaceRepository {
    private final Connection connection;

    public EspaceRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Espace ajouterEspace(Espace espace, UUID managerId) throws SQLException {
        String sql = "INSERT INTO espace (id, nom, type, capacite, equipements, description, prix, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, UUID.randomUUID());
            stmt.setString(2, espace.getNom());
            stmt.setString(3, espace.getType().name());
            stmt.setInt(4, espace.getCapacite());
            String[] equipementsArray = espace.getEquipements().toArray(new String[0]);
            Array equipementsSqlArray = this.connection.createArrayOf("VARCHAR", equipementsArray);
            stmt.setArray(5, equipementsSqlArray);
            stmt.setString(6, espace.getDescription());
            stmt.setBigDecimal(7, espace.getPrix());
            stmt.setObject(8, managerId);

            if (stmt.executeUpdate() > 0) {
                return espace;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'espace : " + e.getMessage());
            throw e;
        }

        return null;
    }

    @Override
    public Espace modifierEspace(Espace espace, UUID managerId) throws SQLException {
        String sql = "UPDATE espace SET nom = ?, type = ?, capacite = ?, equipements = ?, description = ?, prix = ?, manager_id = ? WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, espace.getNom());
            stmt.setString(2, espace.getType().name());
            stmt.setInt(3, espace.getCapacite());
            stmt.setArray(4, connection.createArrayOf("TEXT", espace.getEquipements().toArray(new String[0])));
            stmt.setString(5, espace.getDescription());
            stmt.setBigDecimal(6, espace.getPrix());
            stmt.setObject(7, managerId); // Ajouter l'ID du manager
            stmt.setObject(8, espace.getId());

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



    @Override
    public List<Espace> getAllEspaces() throws SQLException {
        String sql = "SELECT * FROM espace";
        List<Espace> espaces = new ArrayList<>();

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                String typeStr = rs.getString("type");
                TypeEspace type = TypeEspace.valueOf(typeStr);
                int capacite = rs.getInt("capacite");
                Array equipementsArray = rs.getArray("equipements");
                List<String> equipements = equipementsArray != null ?
                        List.of((String[]) equipementsArray.getArray()) : new ArrayList<>();
                String description = rs.getString("description");
                BigDecimal prix = rs.getBigDecimal("prix");
                UUID managerId = (UUID) rs.getObject("manager_id");

                Espace espace = new Espace(id, nom, type, capacite, equipements, description, prix, managerId);
                espaces.add(espace);
            }
        }
        return espaces;
    }
    public Espace getEspaceById(UUID id) throws SQLException {
        String sql = "SELECT * FROM espace WHERE id = ?";

        try (Connection connection = JdbcConnection.getConnection()
                .orElseThrow(() -> new SQLException("Failed to establish a database connection"));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String typeStr = rs.getString("type");
                    TypeEspace type = TypeEspace.valueOf(typeStr);
                    int capacite = rs.getInt("capacite");
                    Array equipementsArray = rs.getArray("equipements");
                    List<String> equipements = equipementsArray != null ?
                            List.of((String[]) equipementsArray.getArray()) : new ArrayList<>();
                    String description = rs.getString("description");
                    BigDecimal prix = rs.getBigDecimal("prix");
                    UUID managerId = (UUID) rs.getObject("manager_id");

                    return new Espace(id, nom, type, capacite, equipements, description, prix, managerId);
                }
            }
        }

        return null;
    }

}
