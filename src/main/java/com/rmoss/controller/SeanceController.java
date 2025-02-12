package com.rmoss.controller;

import com.rmoss.model.Film;
import com.rmoss.model.Salle;
import com.rmoss.model.Seance;
import com.rmoss.model.SeanceService;
import com.rmoss.view.EcranGestionSeances;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SeanceController {

    private SeanceService seanceService;
    private EcranGestionSeances vue;

    public SeanceController(SeanceService seanceService, EcranGestionSeances vue) {
        this.seanceService = seanceService;
        this.vue = vue;
        // Listeners seront ajoutés dans la vue
    }

    public void planifierSeance() {
        // 1. Récupérer les données du formulaire
        Film filmSelectionne = vue.getFormulaireSeance().getFilmComboBox().getItemAt(vue.getFormulaireSeance().getFilmComboBox().getSelectedIndex());
        Salle salleSelectionnee = vue.getFormulaireSeance().getSalleComboBox().getItemAt(vue.getFormulaireSeance().getSalleComboBox().getSelectedIndex());

        String dateHeureString = vue.getFormulaireSeance().getDateHeureTextField().getText();
        LocalDateTime dateHeure = null;
        try {
            dateHeure = LocalDateTime.parse(dateHeureString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Format à adapter selon le champ de saisie
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(vue, "Format de date/heure invalide. Utilisez yyyy-MM-dd HH:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String version = vue.getFormulaireSeance().getVersionTextField().getText();
        String typeProjection = vue.getFormulaireSeance().getTypeProjectionTextField().getText();
        double prixPlace = 0.0;
        try {
            prixPlace = Double.parseDouble(vue.getFormulaireSeance().getPrixPlaceTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Prix de place invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Valider les données
        if (filmSelectionne == null || salleSelectionnee == null || dateHeure == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un film, une salle et une date/heure.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Créer un objet Seance
        Seance nouvelleSeance = new Seance();
        nouvelleSeance.setFilm(filmSelectionne);
        nouvelleSeance.setSalle(salleSelectionnee);
        nouvelleSeance.setDateHeure(dateHeure);
        nouvelleSeance.setVersion(version);
        nouvelleSeance.setTypeProjection(typeProjection);
        nouvelleSeance.setPrixPlace(prixPlace);

        // 4. Appeler le service pour planifier la séance
        Seance seancePlanifiee = seanceService.planifierSeance(nouvelleSeance);

        if (seancePlanifiee != null) {
            // 5. Mettre à jour la vue
            JOptionPane.showMessageDialog(vue, "Séance planifiée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeSeances();
            vue.getFormulaireSeance().viderFormulaire();
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de la planification de la séance.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierSeance() {
        Seance seanceSelectionnee = vue.getSeanceSelectionneeDansTable();
        if (seanceSelectionnee == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une séance à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Récupérer les données modifiées du formulaire
        Film filmSelectionne = vue.getFormulaireSeance().getFilmComboBox().getItemAt(vue.getFormulaireSeance().getFilmComboBox().getSelectedIndex());
        Salle salleSelectionnee = vue.getFormulaireSeance().getSalleComboBox().getItemAt(vue.getFormulaireSeance().getSalleComboBox().getSelectedIndex());
        String dateHeureString = vue.getFormulaireSeance().getDateHeureTextField().getText();
        LocalDateTime dateHeure = null;
        try {
            dateHeure = LocalDateTime.parse(dateHeureString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Format à adapter
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(vue, "Format de date/heure invalide. Utilisez yyyy-MM-dd HH:mm", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String version = vue.getFormulaireSeance().getVersionTextField().getText();
        String typeProjection = vue.getFormulaireSeance().getTypeProjectionTextField().getText();
        double prixPlace = 0.0;
        try {
            prixPlace = Double.parseDouble(vue.getFormulaireSeance().getPrixPlaceTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Prix de place invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Valider les données (similaire à la planification)

        // 3. Mettre à jour l'objet Seance sélectionné
        seanceSelectionnee.setFilm(filmSelectionne);
        seanceSelectionnee.setSalle(salleSelectionnee);
        seanceSelectionnee.setDateHeure(dateHeure);
        seanceSelectionnee.setVersion(version);
        seanceSelectionnee.setTypeProjection(typeProjection);
        seanceSelectionnee.setPrixPlace(prixPlace);

        // 4. Appeler le service pour modifier la séance
        Seance seanceModifiee = seanceService.modifierSeance(seanceSelectionnee);

        if (seanceModifiee != null) {
            // 5. Mettre à jour la vue
            JOptionPane.showMessageDialog(vue, "Séance modifiée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeSeances();
            vue.getFormulaireSeance().viderFormulaire();
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de la modification de la séance.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerSeance() {
        Seance seanceSelectionnee = vue.getSeanceSelectionneeDansTable();
        if (seanceSelectionnee == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une séance à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(vue,
                "Êtes-vous sûr de vouloir supprimer la séance du film : " + seanceSelectionnee.getFilm().getTitre() + " du " + seanceSelectionnee.getDateHeure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " ?",
                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Appeler le service pour supprimer la séance
            boolean suppressionReussie = seanceService.supprimerSeance(seanceSelectionnee.getSeanceId());

            if (suppressionReussie) {
                JOptionPane.showMessageDialog(vue, "Séance supprimée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                vue.mettreAJourListeSeances();
                vue.getFormulaireSeance().viderFormulaire();
            } else {
                JOptionPane.showMessageDialog(vue, "Erreur lors de la suppression de la séance.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void chargerSeancePourModification() {
        Seance seanceSelectionnee = vue.getSeanceSelectionneeDansTable();
        if (seanceSelectionnee != null) {
            vue.getFormulaireSeance().remplirFormulaireAvecSeance(seanceSelectionnee);
        } else {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une séance à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void rechercherSeances(String critere) {
        vue.filtrerSeancesDansTable(critere); // Appel direct à la vue pour le filtrage
    }
}