package com.rmoss.model;

public class Salle {
    private String salleId; // Identifiant unique de la salle
    private String nom;
    private int capacite; // Nombre total de places
    private int nbRangees;
    private int nbColonnes;
    private String typeSalle; // e.g., "Standard", "IMAX", "VIP"

    // Constructeurs
    public Salle() {
        // Constructeur par défaut
    }

    public Salle(String salleId, String nom, int capacite, int nbRangees, int nbColonnes, String typeSalle) {
        this.salleId = salleId;
        this.nom = nom;
        this.capacite = capacite;
        this.nbRangees = nbRangees;
        this.nbColonnes = nbColonnes;
        this.typeSalle = typeSalle;
    }

    // Getters et Setters
    public String getSalleId() {
        return salleId;
    }

    public void setSalleId(String salleId) {
        this.salleId = salleId;
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

    public int getNbRangees() {
        return nbRangees;
    }

    public void setNbRangees(int nbRangees) {
        this.nbRangees = nbRangees;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    public String getTypeSalle() {
        return typeSalle;
    }

    public void setTypeSalle(String typeSalle) {
        this.typeSalle = typeSalle;
    }

    @Override
    public String toString() {
        return nom; // Pour afficher le nom de la salle dans les listes déroulantes, etc.
    }
}