package com.rmoss.controller;

import com.rmoss.model.Film;
import com.rmoss.model.FilmService;
import com.rmoss.view.EcranGestionFilms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilmController {

    private FilmService filmService;
    private EcranGestionFilms vue; // Référence à la vue associée

    public FilmController(FilmService filmService, EcranGestionFilms vue) {
        this.filmService = filmService;
        this.vue = vue;
        // Ajouter les listeners aux boutons de la vue (sera fait plus tard, dans la vue elle-même)
    }

    // Méthodes de gestion des actions utilisateur (appelées par la vue)

    public void ajouterFilm() {
        // 1. Récupérer les données du formulaire de la vue
 String dateSortieString = vue.getFormulaireFilm().getDateSortieTextField().getText();
        Date dateSortie = null; // Initialisation à null au cas où la conversion échoue
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date attendu (YYYY-MM-DD)

        try {
            dateSortie = dateFormat.parse(dateSortieString); // Tenter de parser la chaîne en Date
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(vue, "Format de date de sortie invalide. Veuillez utiliser le format YYYY-MM-DD.", "Erreur de date", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si la date n'est pas au bon format
        }        String realisateur = vue.getFormulaireFilm().getRealisateurTextField().getText();
        String synopsis = vue.getFormulaireFilm().getSynopsisTextArea().getText();
        int duree = 0; // Initialisation par défaut, à valider et récupérer du formulaire
        try {
            duree = Integer.parseInt(vue.getFormulaireFilm().getDureeTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Durée invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si la durée est invalide
        }
        String genre = vue.getFormulaireFilm().getGenreTextField().getText();
        String affichePath = vue.getFormulaireFilm().getAffichePathTextField().getText();
        String bandeAnnonceURL = vue.getFormulaireFilm().getBandeAnnonceURLTextField().getText();
        String classification = vue.getFormulaireFilm().getClassificationTextField().getText();

        // 2. Valider les données (ici validation basique, à améliorer)
        String titre = vue.getFormulaireFilm().getTitreTextField().getText();
        if (titre == null || titre.trim().isEmpty() || realisateur == null || realisateur.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Le titre et le réalisateur sont obligatoires.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return; // Sortir si les champs obligatoires sont vides
        }
        if (dateSortie == null) {
            JOptionPane.showMessageDialog(vue, "La date de sortie est obligatoire.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // 3. Créer un objet Film avec les données
        Film nouveauFilm = new Film();
        nouveauFilm.setTitre(titre);
        nouveauFilm.setRealisateur(realisateur);
        nouveauFilm.setSynopsis(synopsis);
        nouveauFilm.setDuree(duree);
        nouveauFilm.setGenre(genre);
        nouveauFilm.setDateSortie(dateSortie);
        nouveauFilm.setAffichePath(affichePath);
        nouveauFilm.setBandeAnnonceURL(bandeAnnonceURL);
        nouveauFilm.setClassification(classification);

        // 4. Appeler le service pour ajouter le film au modèle
        Film filmAjoute = filmService.ajouterFilm(nouveauFilm);

        if (filmAjoute != null) {
            // 5. Mettre à jour la vue (afficher un message de succès, rafraîchir la liste des films)
            JOptionPane.showMessageDialog(vue, "Film ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeFilms(); // Méthode à implémenter dans la vue pour rafraîchir l'affichage
            vue.getFormulaireFilm().viderFormulaire(); // Vider le formulaire après l'ajout
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de l'ajout du film.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierFilm() {
        Film filmSelectionne = vue.getFilmSelectionneDansTable();
        if (filmSelectionne == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un film à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Récupérer les données modifiées du formulaire de la vue
        String titre = vue.getFormulaireFilm().getTitreTextField().getText();
        String realisateur = vue.getFormulaireFilm().getRealisateurTextField().getText();
        String synopsis = vue.getFormulaireFilm().getSynopsisTextArea().getText();
        int duree = 0;
        try {
            duree = Integer.parseInt(vue.getFormulaireFilm().getDureeTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Durée invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String genre = vue.getFormulaireFilm().getGenreTextField().getText();
 String dateSortieString = vue.getFormulaireFilm().getDateSortieTextField().getText();
        Date dateSortie = null; // Initialisation à null au cas où la conversion échoue
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de date attendu (YYYY-MM-DD)

        try {
            dateSortie = dateFormat.parse(dateSortieString); // Tenter de parser la chaîne en Date
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(vue, "Format de date de sortie invalide. Veuillez utiliser le format YYYY-MM-DD.", "Erreur de date", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si la date n'est pas au bon format
        }        String affichePath = vue.getFormulaireFilm().getAffichePathTextField().getText();
        String bandeAnnonceURL = vue.getFormulaireFilm().getBandeAnnonceURLTextField().getText();
        String classification = vue.getFormulaireFilm().getClassificationTextField().getText();

        // 2. Valider les données (similaire à l'ajout)

        // 3. Mettre à jour l'objet Film sélectionné avec les nouvelles données
        filmSelectionne.setTitre(titre);
        filmSelectionne.setRealisateur(realisateur);
        filmSelectionne.setSynopsis(synopsis);
        filmSelectionne.setDuree(duree);
        filmSelectionne.setGenre(genre);
        filmSelectionne.setDateSortie(dateSortie);
        filmSelectionne.setAffichePath(affichePath);
        filmSelectionne.setBandeAnnonceURL(bandeAnnonceURL);
        filmSelectionne.setClassification(classification);

        // 4. Appeler le service pour modifier le film dans le modèle
        Film filmModifie = filmService.modifierFilm(filmSelectionne); // On passe l'objet filmSelectionne qui a été mis à jour

        if (filmModifie != null) {
            // 5. Mettre à jour la vue (message de succès, rafraîchir la liste)
            JOptionPane.showMessageDialog(vue, "Film modifié avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeFilms(); // Rafraîchir la liste
            vue.getFormulaireFilm().viderFormulaire(); // Vider formulaire après modification
              vue.mettreAJourListeFilms(); // Refresh the film list
    // You might also need to refresh seanceService if it holds film data
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de la modification du film.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerFilm() {
        Film filmSelectionne = vue.getFilmSelectionneDansTable();
        if (filmSelectionne == null) {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un film à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(vue,
                "Êtes-vous sûr de vouloir supprimer le film : " + filmSelectionne.getTitre() + " ?",
                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Appeler le service pour supprimer le film par ID
            boolean suppressionReussie = filmService.supprimerFilm(filmSelectionne.getFilmId());

            if (suppressionReussie) {
                JOptionPane.showMessageDialog(vue, "Film supprimé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                vue.mettreAJourListeFilms(); // Rafraîchir la liste
                vue.getFormulaireFilm().viderFormulaire(); // Vider le formulaire
            } else {
                JOptionPane.showMessageDialog(vue, "Erreur lors de la suppression du film.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void rechercherFilms(String critere) {
        // Pour l'instant, la recherche est gérée directement dans la vue (filtrage du tableau).
        // Si la recherche devient plus complexe (ex: recherche côté serveur, indexation...),
        // on pourrait ajouter une méthode dans le service et l'appeler ici.
        vue.filtrerFilmsDansTable(critere); // On appelle directement une méthode de la vue pour filtrer
    }

    public void chargerFilmPourModification() {
        Film filmSelectionne = vue.getFilmSelectionneDansTable();
        if (filmSelectionne != null) {
            vue.getFormulaireFilm().remplirFormulaireAvecFilm(filmSelectionne);
        } else {
            JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un film à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }
}