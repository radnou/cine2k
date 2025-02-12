package com.rmoss.controller;

import com.rmoss.model.Salle;
import com.rmoss.model.SalleService;
import com.rmoss.view.EcranGestionSalles;

import javax.swing.*;

public class SalleController {

    private SalleService salleService;
    private EcranGestionSalles vue;

    public SalleController(SalleService salleService, EcranGestionSalles vue) {
        this.salleService = salleService;
        this.vue = vue;
    }

    public void ajouterSalle() {
        // 1. Récupérer les données du formulaire de la vue EcranGestionSalles
        String nom = vue.getFormulaireSalle().getNomTextField().getText();
        int capacite = 0;
        try {
            capacite = Integer.parseInt(vue.getFormulaireSalle().getCapaciteTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Capacité invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int nbRangees = 0;
        try {
            nbRangees = Integer.parseInt(vue.getFormulaireSalle().getNbRangeesTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Nombre de rangées invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int nbColonnes = 0;
        try {
            nbColonnes = Integer.parseInt(vue.getFormulaireSalle().getNbColonnesTextField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vue, "Nombre de colonnes invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String typeSalle = vue.getFormulaireSalle().getTypeSalleTextField().getText();


        // 2. Valider les données (ajouter validation si nécessaire)

        // 3. Créer un objet Salle
        Salle nouvelleSalle = new Salle();
        nouvelleSalle.setNom(nom);
        nouvelleSalle.setCapacite(capacite);
        nouvelleSalle.setNbRangees(nbRangees);
        nouvelleSalle.setNbColonnes(nbColonnes);
        nouvelleSalle.setTypeSalle(typeSalle);


        // 4. Appeler le service pour ajouter la salle
        Salle salleAjoutee = salleService.ajouterSalle(nouvelleSalle);

        if (salleAjoutee != null) {
            // 5. Mettre à jour la vue
            JOptionPane.showMessageDialog(vue, "Salle ajoutée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            vue.mettreAJourListeSalles(); // Méthode à implémenter dans EcranGestionSalles
            vue.getFormulaireSalle().viderFormulaire(); // Vider le formulaire
        } else {
            JOptionPane.showMessageDialog(vue, "Erreur lors de l'ajout de la salle.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ... (ajouter les méthodes modifierSalle, supprimerSalle, etc. - en suivant le modèle de FilmController)
    public void modifierSalle() { /* ... */ }
    public void supprimerSalle() { /* ... */ }
    public void chargerSallePourModification() { /* ... */ }

    public void rechercherSalles(String texteRecherche) {
    }
}