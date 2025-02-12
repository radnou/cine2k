package com.rmoss.model;

import java.util.Date;

public class Film {
    private String filmId; // Identifiant unique du film
    private String titre;
    private String realisateur;
    private String synopsis;
    private int duree; // en minutes
    private String genre;
    private Date dateSortie;
    private String affichePath; // Chemin vers le fichier image de l'affiche
    private String bandeAnnonceURL; // URL de la bande-annonce
    private String classification; // e.g., "Tout public", "12+", "16+"

    // Constructeurs
    public Film() {
        // Constructeur par défaut
    }

    public Film(String filmId, String titre, String realisateur, String synopsis, int duree, String genre, Date dateSortie, String affichePath, String bandeAnnonceURL, String classification) {
        this.filmId = filmId;
        this.titre = titre;
        this.realisateur = realisateur;
        this.synopsis = synopsis;
        this.duree = duree;
        this.genre = genre;
        this.dateSortie = dateSortie;
        this.affichePath = affichePath;
        this.bandeAnnonceURL = bandeAnnonceURL;
        this.classification = classification;
    }

    // Getters et Setters
    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getAffichePath() {
        return affichePath;
    }

    public void setAffichePath(String affichePath) {
        this.affichePath = affichePath;
    }

    public String getBandeAnnonceURL() {
        return bandeAnnonceURL;
    }

    public void setBandeAnnonceURL(String bandeAnnonceURL) {
        this.bandeAnnonceURL = bandeAnnonceURL;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return titre; // Pour afficher le titre du film dans les listes déroulantes, etc.
    }
}