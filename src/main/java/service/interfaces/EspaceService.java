package service.interfaces;

import model.Espace;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface EspaceService {

    Espace ajouterEspace(Espace espace, UUID managerId) throws SQLException;

    Espace modifierEspace(Espace espace, UUID managerId) throws SQLException;

    void supprimerEspace(UUID id) throws SQLException;

    List<Espace> getAllEspaces() throws SQLException;
}
