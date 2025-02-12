package com.rmoss.model;

import java.time.LocalDateTime;

public class Reservation {
    private String reservationId; // Identifiant unique de la réservation
    private Seance seance; // Relation avec la classe Seance
    private String clientNom;
    private String clientPrenom;
    private int nbPlaces;
    private String typeTarif; // e.g., "Plein tarif", "Tarif réduit", "Étudiant"
    private LocalDateTime dateReservation;
    private String statut; // e.g., "Confirmée", "En attente", "Annulée", "Payée"

    // Constructeurs
    public Reservation() {
        // Constructeur par défaut
    }

    public Reservation(String reservationId, Seance seance, String clientNom, String clientPrenom, int nbPlaces, String typeTarif, LocalDateTime dateReservation, String statut) {
        this.reservationId = reservationId;
        this.seance = seance;
        this.clientNom = clientNom;
        this.clientPrenom = clientPrenom;
        this.nbPlaces = nbPlaces;
        this.typeTarif = typeTarif;
        this.dateReservation = dateReservation;
        this.statut = statut;
    }

    // Getters et Setters
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientPrenom() {
        return clientPrenom;
    }

    public void setClientPrenom(String clientPrenom) {
        this.clientPrenom = clientPrenom;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public String getTypeTarif() {
        return typeTarif;
    }

    public void setTypeTarif(String typeTarif) {
        this.typeTarif = typeTarif;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Réservation N°" + reservationId + " - Séance: " + seance + " - Client: " + clientNom + " " + clientPrenom; // Affichage pour les listes
    }
}