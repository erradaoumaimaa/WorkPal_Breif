package model;
import Enum.*;

import java.util.UUID;

public class Gestionnaire extends User {

    // Constructeur
    public Gestionnaire(UUID id, String name, String email, String password, UserRole role) {
        super(id, name, email, password, role);
    }

}
