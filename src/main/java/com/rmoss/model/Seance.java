package com.rmoss.model;

import java.time.LocalDateTime;

public class Seance {
    private String seanceId; // Identifiant unique de la séance
    private Film film; // Relation avec la classe Film
    private Salle salle; // Relation avec la classe Salle
    private LocalDateTime dateHeure; // Date et heure de la séance
    private String version; // e.g., "VF", "VOST"
    private String typeProjection; // e.g., "2D", "3D", "IMAX"
    private double prixPlace;

    // Constructeurs
    public Seance() {
        // Constructeur par défaut
    }

    public Seance(String seanceId, Film film, Salle salle, LocalDateTime dateHeure, String version, String typeProjection, double prixPlace) {
        this.seanceId = seanceId;
        this.film = film;
        this.salle = salle;
        this.dateHeure = dateHeure;
        this.version = version;
        this.typeProjection = typeProjection;
        this.prixPlace = prixPlace;
    }

    // Getters et Setters
    public String getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(String seanceId) {
        this.seanceId = seanceId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTypeProjection() {
        return typeProjection;
    }

    public void setTypeProjection(String typeProjection) {
        this.typeProjection = typeProjection;
    }

    public double getPrixPlace() {
        return prixPlace;
    }

    public void setPrixPlace(double prixPlace) {
        this.prixPlace = prixPlace;
    }

    @Override
    public String toString() {
        return film.getTitre() + " - " + dateHeure.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); // Affichage pour les listes
    }
}