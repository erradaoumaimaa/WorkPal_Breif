package repository.interfaces;

import model.Espace;

import java.sql.SQLException;
import java.util.UUID;

public interface EspaceRepository {

    Espace ajouterEspace(Espace espace)throws SQLException;

    Espace modifierEspace(Espace espace) throws SQLException;

    void supprimerEspace(UUID id) throws SQLException; //
}
