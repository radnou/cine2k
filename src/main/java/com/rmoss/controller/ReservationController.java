package com.rmoss.controller;

import com.rmoss.model.Reservation;
import com.rmoss.model.ReservationService;
import com.rmoss.model.Seance;
import com.rmoss.model.SeanceService;
import com.rmoss.view.EcranReservations;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationController {

    private ReservationService reservationService;
    private SeanceService seanceService; // Peut-être besoin pour récupérer les séances pour la vue réservation
    private EcranReservations vue;

    public ReservationController(ReservationService reservationService, SeanceService seanceService, EcranReservations vue) {
        this.reservationService = reservationService;
        this.seanceService = seanceService;
        this.vue = vue;
        // Listeners à ajouter dans la vue
    }

    public void creerReservation() {
        // 1. Récupérer les données du formulaire
        Seance seanceSelectionnee = vue.getFormulaireReservation().getSeanceComboBox().getItemAt(vue.getFormulaireReservation().getSeanceComboBox().getSelectedIndex());
        String clientNom = vue.getFormulaireReservation().getClientNomTextField().getText();
        String clientPrenom = vue.getFormulaireReservation().getClientPrenomTextField().getText();
        int nbPlaces = 0;
        try {
            nbPlaces = Integer.parseInt(vue.getFormulaireReservation().getNbPlacesTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Nombre de places invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String typeTarif = vue.getFormulaireReservation().getTypeTarifTextField().getText();
        String statut = vue.getFormulaireReservation().getStatutTextField().getText(); // Ou gérer le statut par défaut coté Controller/Service

        // 2. Valider les données
        if (seanceSelectionnee == null || clientNom == null || clientNom.trim().isEmpty() || nbPlaces <= 0) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une séance, entrer un nom de client et un nombre de places valide.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Créer un objet Reservation
        Reservation nouvelleReservation = new Reservation();
        nouvelleReservation.setSeance(seanceSelectionnee);
        nouvelleReservation.setClientNom(clientNom);
        nouvelleReservation.setClientPrenom(clientPrenom);
        nouvelleReservation.setNbPlaces(nbPlaces);
        nouvelleReservation.setTypeTarif(typeTarif);
        nouvelleReservation.setDateReservation(LocalDateTime.now()); // Date de réservation = maintenant
        nouvelleReservation.setStatut(statut); // Ou statut par défaut: "Confirmée" par exemple

        // 4. Appeler le service pour créer la réservation
        Reservation reservationCreee = reservationService.creerReservation(nouvelleReservation);

        if (reservationCreee != null) {
            // 5. Mettre à jour la vue
            JOptionPane.showMessageDialog(vue, "Réservation créée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeReservations();
            vue.getFormulaireReservation().viderFormulaire();
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de la création de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierReservation() {
        Reservation reservationSelectionnee = vue.getReservationSelectionneeDansTable();
        if (reservationSelectionnee == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une réservation à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Récupérer les données modifiées du formulaire
        Seance seanceSelectionnee = vue.getFormulaireReservation().getSeanceComboBox().getItemAt(vue.getFormulaireReservation().getSeanceComboBox().getSelectedIndex());
        String clientNom = vue.getFormulaireReservation().getClientNomTextField().getText();
        String clientPrenom = vue.getFormulaireReservation().getClientPrenomTextField().getText();
        int nbPlaces = 0;
        try {
            nbPlaces = Integer.parseInt(vue.getFormulaireReservation().getNbPlacesTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Nombre de places invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String typeTarif = vue.getFormulaireReservation().getTypeTarifTextField().getText();
        String statut = vue.getFormulaireReservation().getStatutTextField().getText();

        // 2. Valider les données (similaire à la création)

        // 3. Mettre à jour l'objet Reservation sélectionné
        reservationSelectionnee.setSeance(seanceSelectionnee);
        reservationSelectionnee.setClientNom(clientNom);
        reservationSelectionnee.setClientPrenom(clientPrenom);
        reservationSelectionnee.setNbPlaces(nbPlaces);
        reservationSelectionnee.setTypeTarif(typeTarif);
        reservationSelectionnee.setStatut(statut);

        // 4. Appeler le service pour modifier la réservation
        Reservation reservationModifiee = reservationService.modifierReservation(reservationSelectionnee);

        if (reservationModifiee != null) {
            // 5. Mettre à jour la vue
            JOptionPane.showMessageDialog(vue, "Réservation modifiée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeReservations();
            vue.getFormulaireReservation().viderFormulaire();
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de la modification de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void annulerReservation() { // Nommé "annuler" pour coller au vocabulaire métier
        Reservation reservationSelectionnee = vue.getReservationSelectionneeDansTable();
        if (reservationSelectionnee == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une réservation à annuler.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(vue,
                "Êtes-vous sûr de vouloir annuler la réservation N° " + reservationSelectionnee.getReservationId() + " pour la séance du film : " + reservationSelectionnee.getSeance().getFilm().getTitre() + " du " + reservationSelectionnee.getSeance().getDateHeure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " ?",
                "Confirmation d'annulation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Appeler le service pour annuler la réservation
            boolean annulationReussie = reservationService.annulerReservation(reservationSelectionnee.getReservationId());

            if (annulationReussie) {
                JOptionPane.showMessageDialog(vue, "Réservation annulée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                vue.mettreAJourListeReservations();
                vue.getFormulaireReservation().viderFormulaire();
            } else {
                JOptionPane.showMessageDialog(vue, "Erreur lors de l'annulation de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void chargerReservationPourModification() {
        Reservation reservationSelectionnee = vue.getReservationSelectionneeDansTable();
        if (reservationSelectionnee != null) {
            vue.getFormulaireReservation().remplirFormulaireAvecReservation(reservationSelectionnee);
        } else {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une réservation à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void rechercherReservations(String critere) {
        vue.filtrerReservationsDansTable(critere); // Appel direct à la vue pour le filtrage
    }
}