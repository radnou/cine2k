package com.rmoss.view;

import javax.swing.*;
import java.awt.*;

import com.rmoss.model.Film;

public class FormulaireFilm extends JPanel {

    private JTextField titreTextField;
    private JTextField realisateurTextField;
    private JTextArea synopsisTextArea;
    private JTextField dureeTextField;
    private JTextField genreTextField;
    private JTextField dateSortieTextField; // Remplaçons JDateChooser par JTextField pour simplifier pour l'instant
    private JTextField affichePathTextField;
    private JTextField bandeAnnonceURLTextField;
    private JTextField classificationTextField;

    public FormulaireFilm() {
        setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour un placement plus précis

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marges autour des composants
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche par défaut

        // Ligne 1 : Titre
        JLabel titreLabel = new JLabel("Titre:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(titreLabel, gbc);

        titreTextField = new JTextField(25); // 25 colonnes de largeur
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; // Remplir l'espace horizontal
        add(titreTextField, gbc);
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE; // Reset weightx et fill pour les composants suivants

        // Ligne 2 : Réalisateur
        JLabel realisateurLabel = new JLabel("Réalisateur:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(realisateurLabel, gbc);

        realisateurTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(realisateurTextField, gbc);

        // Ligne 3 : Genre
        JLabel genreLabel = new JLabel("Genre:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(genreLabel, gbc);

        genreTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(genreTextField, gbc);

        // Ligne 4 : Durée
        JLabel dureeLabel = new JLabel("Durée (minutes):");
        gbc.gridx = 0; gbc.gridy = 3;
        add(dureeLabel, gbc);

        dureeTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(dureeTextField, gbc);

        // Ligne 5 : Date de Sortie
        JLabel dateSortieLabel = new JLabel("Date de Sortie (YYYY-MM-DD):");
        gbc.gridx = 0; gbc.gridy = 4;
        add(dateSortieLabel, gbc);

        dateSortieTextField = new JTextField(25); // Simplification: JTextField au lieu de JDateChooser
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(dateSortieTextField, gbc);

        // Ligne 6 : Classification
        JLabel classificationLabel = new JLabel("Classification:");
        gbc.gridx = 0; gbc.gridy = 5;
        add(classificationLabel, gbc);

        classificationTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(classificationTextField, gbc);

        // Ligne 7 : Affiche (Chemin du fichier)
        JLabel affichePathLabel = new JLabel("Chemin Affiche:");
        gbc.gridx = 0; gbc.gridy = 6;
        add(affichePathLabel, gbc);

        affichePathTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 6; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(affichePathTextField, gbc);

        // Ligne 8 : Bande-annonce (URL)
        JLabel bandeAnnonceURLLabel = new JLabel("URL Bande-annonce:");
        gbc.gridx = 0; gbc.gridy = 7;
        add(bandeAnnonceURLLabel, gbc);

        bandeAnnonceURLTextField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(bandeAnnonceURLTextField, gbc);

        // Ligne 9 : Synopsis
        JLabel synopsisLabel = new JLabel("Synopsis:");
        gbc.gridx = 0; gbc.gridy = 8; gbc. weighty = 1.0; gbc.fill = GridBagConstraints.VERTICAL; // weightY et fill pour étirer verticalement
        gbc.anchor = GridBagConstraints.NORTHWEST; // Aligner le label en haut à gauche
        add(synopsisLabel, gbc);
        gbc.weighty = 0.0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST; // Reset weightY et fill

        synopsisTextArea = new JTextArea(5, 25); // 5 lignes, 25 colonnes
        synopsisTextArea.setLineWrap(true); // Retour à la ligne automatique
        synopsisTextArea.setWrapStyleWord(true); // Retour à la ligne par mot
        JScrollPane synopsisScrollPane = new JScrollPane(synopsisTextArea); // Pour ajouter des ascenseurs si le texte est long
        gbc.gridx = 1; gbc.gridy = 8; gbc.fill = GridBagConstraints.BOTH; // Remplir l'espace horizontal et vertical disponible
        add(synopsisScrollPane, gbc);


        // Bordure pour délimiter le formulaire (optionnel, juste pour la visualisation)
        setBorder(BorderFactory.createTitledBorder("Informations du Film"));
    }

    // Getters pour accéder aux champs depuis le contrôleur
    public JTextField getTitreTextField() {
        return titreTextField;
    }

    public JTextField getRealisateurTextField() {
        return realisateurTextField;
    }

    public JTextArea getSynopsisTextArea() {
        return synopsisTextArea;
    }

    public JTextField getDureeTextField() {
        return dureeTextField;
    }

    public JTextField getGenreTextField() {
        return genreTextField;
    }

    public JTextField getDateSortieTextField() {
        return dateSortieTextField;
    }

    public JTextField getAffichePathTextField() {
        return affichePathTextField;
    }

    public JTextField getBandeAnnonceURLTextField() {
        return bandeAnnonceURLTextField;
    }

    public JTextField getClassificationTextField() {
        return classificationTextField;
    }

    public void viderFormulaire() {
        titreTextField.setText("");
        realisateurTextField.setText("");
        synopsisTextArea.setText("");
        dureeTextField.setText("");
        genreTextField.setText("");
        dateSortieTextField.setText(""); // ou dateSortieDateChooser.setDate(null); si vous utilisez JDateChooser
        affichePathTextField.setText("");
        bandeAnnonceURLTextField.setText("");
        classificationTextField.setText("");
    }

    public void remplirFormulaireAvecFilm(Film film) {
        titreTextField.setText(film.getTitre());
        realisateurTextField.setText(film.getRealisateur());
        synopsisTextArea.setText(film.getSynopsis());
        dureeTextField.setText(String.valueOf(film.getDuree()));
        genreTextField.setText(film.getGenre());
        dateSortieTextField.setText(film.getDateSortie() != null ? film.getDateSortie().toString() : ""); // Formatage de la date si nécessaire

        affichePathTextField.setText(film.getAffichePath());
        bandeAnnonceURLTextField.setText(film.getBandeAnnonceURL());
        classificationTextField.setText(film.getClassification());
    }
}