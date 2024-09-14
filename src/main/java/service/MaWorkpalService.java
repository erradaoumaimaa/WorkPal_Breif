package service;

import db.JdbcConnection;
import model.Espace;
import model.User;
import repository.implementation.EspaceRepositoryImpl;
import repository.interfaces.UserRepository;
import repository.implementation.UserRepositoryImpl;
import service.implementation.AuthService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import Enum.*;

public class MaWorkpalService {
    private static MaWorkpalService instance;
    private final AuthService authService;
    private Connection connection;

    private MaWorkpalService() throws SQLException {

        this.connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("Erreur de connexion"));
        UserRepository userRepository = new UserRepositoryImpl(connection);
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
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Initialisation et affichage du menu de connexion
        System.out.println("************************************************************");
        System.out.println("                  Bonjour en Workpal !                       ");
        System.out.println("                                                            ");
        System.out.println("  Login                                     ");
        System.out.println("************************************************************");

        User user = authService.authenticate(scanner);
        if (user != null) {
            System.out.println("Login réussi: " + user);
            if (UserRole.MANAGER.name().equals(user.getRole().name())) {
                UUID managerId = user.getId();
                handleManagerMenu(scanner, managerId);
            } else {
                System.out.println("Accès non autorisé.");
            }
        } else {
            System.out.println("Échec de l'authentification.");
        }

        scanner.close();
        closeConnection();
    }


    private void handleManagerMenu(Scanner scanner, UUID managerId) {
        boolean running = true;
        while (running) {
            System.out.println("********** Menu Manager **********");
            System.out.println("1. Gérer les espaces");
            System.out.println("2. Voir les réservations");
            System.out.println("3. Log out");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageEspaces(scanner, managerId);
                    break;
                case 2:
                    System.out.println("Voir les réservations");
                    break;
                case 3:
                    System.out.println("Déconnexion...");
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }

    private void manageEspaces(Scanner scanner, UUID managerId) {
        try {
            ensureConnectionIsOpen();
            EspaceRepositoryImpl espaceRepo = new EspaceRepositoryImpl(this.connection);

            System.out.println("1. Ajouter un espace");
            System.out.println("2. Modifier un espace");
            System.out.println("3. Supprimer un espace");
            System.out.println("4. Lister les espaces");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    try {
                        Espace newEspace = createEspaceFromInput(scanner);
                        espaceRepo.ajouterEspace(newEspace, managerId);
                        System.out.println("Espace ajouté avec succès.");
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de l'ajout de l'espace : " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Entrez l'ID de l'espace à modifier : ");
                        UUID espaceId = UUID.fromString(scanner.nextLine());
                        Espace espaceToUpdate = espaceRepo.getEspaceById(espaceId);
                        if (espaceToUpdate != null) {
                            Espace updatedEspace = createEspaceFromInput(scanner);
                            espaceRepo.modifierEspace(updatedEspace, managerId);
                            System.out.println("Espace modifié avec succès.");
                        } else {
                            System.out.println("Espace non trouvé.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la modification de l'espace : " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Entrez l'ID de l'espace à supprimer : ");
                        UUID espaceIdToDelete = UUID.fromString(scanner.nextLine());
                        espaceRepo.supprimerEspace(espaceIdToDelete);
                        System.out.println("Espace supprimé avec succès.");
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la suppression de l'espace : " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        List<Espace> espaces = espaceRepo.getAllEspaces();
                        espaces.forEach(System.out::println);
                    } catch (SQLException e) {
                        System.out.println("Erreur lors de la récupération des espaces : " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

    private void ensureConnectionIsOpen() throws SQLException {
        if (connection == null || connection.isClosed()) {
            this.connection = JdbcConnection.getConnection().orElseThrow(() -> new SQLException("Erreur de connexion"));
        }
    }

    private Espace createEspaceFromInput(Scanner scanner) {
        System.out.println("Entrez le nom de l'espace : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez le type de l'espace (BUREAU, SALLE_REUNION, OPEN_SPACE, ...): ");
        TypeEspace type = TypeEspace.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Entrez la capacité : ");
        int capacite = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Entrez la liste des équipements séparés par une virgule : ");
        String[] equipementsArray = scanner.nextLine().split(",");
        List<String> equipements = Arrays.asList(equipementsArray);

        System.out.println("Entrez la description : ");
        String description = scanner.nextLine();

        System.out.println("Entrez le prix : ");
        BigDecimal prix = scanner.nextBigDecimal();
        scanner.nextLine();

        return new Espace(UUID.randomUUID(), nom, type, capacite, equipements, description, prix, null);
    }
}
