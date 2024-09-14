package service.implementation;

import model.Espace;
import repository.interfaces.EspaceRepository;
import service.interfaces.EspaceService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;

    public EspaceServiceImpl(EspaceRepository espaceRepository) {
        this.espaceRepository = espaceRepository;
    }

    @Override
    public Espace ajouterEspace(Espace espace, UUID managerId) throws SQLException {
        espace.setManagerId(managerId);
        return espaceRepository.ajouterEspace(espace,managerId);
    }

    @Override
    public Espace modifierEspace(Espace espace, UUID managerId) throws SQLException {
        espace.setManagerId(managerId);
        return espaceRepository.modifierEspace(espace, managerId);
    }

    @Override
    public void supprimerEspace(UUID id) throws SQLException {
        espaceRepository.supprimerEspace(id);
    }

    @Override
    public List<Espace> getAllEspaces() throws SQLException {
        return espaceRepository.getAllEspaces();
    }
}
