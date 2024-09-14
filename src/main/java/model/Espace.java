package model;

import Enum.TypeEspace;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Espace {
    private UUID id; // UUID : Universally Unique Identifier
    private String nom;
    private TypeEspace type;
    private int capacite;
    private List<String> equipements;
    private String description;
    private BigDecimal prix;
    private UUID managerId;
    /*
 constructeur
 */
    public Espace(UUID id, String nom, TypeEspace type, int capacite, List<String> equipements, String description, BigDecimal prix, UUID managerId) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.capacite = capacite;
        this.equipements = equipements;
        this.description = description;
        this.prix = prix;
        this.managerId = managerId;
    }

    /*
    ajout getters and setters:
     */

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TypeEspace getType() {
        return type;
    }

    public void setType(TypeEspace type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public List<String> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<String> equipements) {
        this.equipements = equipements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }
    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Espace{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type=" + type +
                ", capacite=" + capacite +
                ", equipements=" + equipements +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", managerId=" + managerId +
                '}';
    }
}