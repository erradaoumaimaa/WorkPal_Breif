package service.implementation;

import model.Espace;
import repository.interfaces.EspaceRepository;
import service.interfaces.EspaceService;

import java.sql.SQLException;
import java.util.UUID;

public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;

    public EspaceServiceImpl(EspaceRepository espaceRepository) {
        this.espaceRepository = espaceRepository;
    }

    @Override
    public Espace ajouterEspace(Espace espace) throws SQLException {
        return espaceRepository.ajouterEspace(espace);
    }

    @Override
    public Espace modifierEspace(Espace espace) throws SQLException {
        return espaceRepository.modifierEspace(espace);
    }

    @Override
    public void supprimerEspace(UUID id) throws SQLException {
        espaceRepository.supprimerEspace(id);
    }
}
