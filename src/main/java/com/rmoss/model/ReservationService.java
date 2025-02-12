package com.rmoss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>(); // Stockage en mémoire

    public ReservationService() {
        // Constructeur par défaut
        // Initialisation de réservations de test possible ici
    }

    public Reservation creerReservation(Reservation reservation) {
        reservation.setReservationId(UUID.randomUUID().toString()); // Générer un ID unique
        reservations.add(reservation);
        return reservation;
    }

    public Reservation modifierReservation(Reservation reservationModifie) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId().equals(reservationModifie.getReservationId())) {
                reservations.set(i, reservationModifie);
                return reservationModifie;
            }
        }
        return null; // Réservation non trouvée
    }

    public boolean annulerReservation(String reservationId) {
        return reservations.removeIf(reservation -> reservation.getReservationId().equals(reservationId));
    }

    public Reservation getReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null; // Réservation non trouvée
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    // Méthodes supplémentaires possibles (filtrer par séance, par client, par date, etc.)
    // ...

    public List<Reservation> getReservationsBySeance(Seance seance) {
        List<Reservation> reservationsSeance = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getSeance() != null && reservation.getSeance().getSeanceId().equals(seance.getSeanceId())) {
                reservationsSeance.add(reservation);
            }
        }
        return reservationsSeance;
    }

    public List<Reservation> getReservationsByClientNom(String clientNom) {
        List<Reservation> reservationsClient = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getClientNom() != null && reservation.getClientNom().equalsIgnoreCase(clientNom)) { // Comparaison insensible à la casse
                reservationsClient.add(reservation);
            }
        }
        return reservationsClient;
    }
}