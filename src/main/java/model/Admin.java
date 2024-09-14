package model;

import java.util.UUID;
import Enum.*;
public class Admin extends User {

    // Constructeur
    public Admin(UUID id, String name, String email, String password, UserRole role) {
        super(id, name, email, password, role);
    }

}
