package com.rmoss.view;

import com.rmoss.model.Film;
import com.rmoss.model.FilmService;
import com.rmoss.model.Salle;
import com.rmoss.model.SalleService;
import com.rmoss.model.Seance;

import javax.swing.*;
import java.awt.*;

public class FormulaireSeance extends JPanel {

    private JComboBox<Film> filmComboBox;
    private JComboBox<Salle> salleComboBox;
    private JTextField dateHeureTextField;
    private JTextField versionTextField;
    private JTextField typeProjectionTextField;
    private JTextField prixPlaceTextField;

    private FilmService filmService;
    private SalleService salleService;

    public FormulaireSeance(FilmService filmService, SalleService salleService) {
        this.filmService = filmService; // Initialisation des services pour remplir les combobox
        this.salleService = salleService;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Ligne 1: Film
        JLabel filmLabel = new JLabel("Film:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(filmLabel, gbc);

        filmComboBox = new JComboBox<>();
        chargerFilmsDansComboBox(); // Méthode pour remplir le ComboBox avec les films
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(filmComboBox, gbc);

        // Ligne 2: Salle
        JLabel salleLabel = new JLabel("Salle:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(salleLabel, gbc);

        salleComboBox = new JComboBox<>();
        chargerSallesDansComboBox(); // Méthode pour remplir le ComboBox avec les salles
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(salleComboBox, gbc);

        // Ligne 3: Date et Heure (YYYY-MM-DD HH:mm)
        JLabel dateHeureLabel = new JLabel("Date et Heure (YYYY-MM-DD HH:mm):");
        gbc.gridx = 0; gbc.gridy = 2;
        add(dateHeureLabel, gbc);

        dateHeureTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(dateHeureTextField, gbc);

        // Ligne 4: Version (VF/VOST)
        JLabel versionLabel = new JLabel("Version (VF/VOST):");
        gbc.gridx = 0; gbc.gridy = 3;
        add(versionLabel, gbc);

        versionTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(versionTextField, gbc);

        // Ligne 5: Type de Projection (2D/3D/IMAX)
        JLabel typeProjectionLabel = new JLabel("Type de Projection (2D/3D/IMAX):");
        gbc.gridx = 0; gbc.gridy = 4;
        add(typeProjectionLabel, gbc);

        typeProjectionTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typeProjectionTextField, gbc);

        // Ligne 6: Prix par Place
        JLabel prixPlaceLabel = new JLabel("Prix par Place:");
        gbc.gridx = 0; gbc.gridy = 5;
        add(prixPlaceLabel, gbc);

        prixPlaceTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(prixPlaceTextField, gbc);


        setBorder(BorderFactory.createTitledBorder("Informations de la Séance"));
    }

    private void chargerFilmsDansComboBox() {
        if (filmService == null) {
            return;
        }
        filmService.getAllFilms().stream().toString();
        filmComboBox.removeAllItems();
        for (Film film : filmService.getAllFilms()) {
            filmComboBox.addItem(film);
        }
    }

    private void chargerSallesDansComboBox() {
        if (salleService == null) {
            return;
        }
        salleComboBox.removeAllItems();
        for (Salle salle : salleService.getAllSalles()) {
            salleComboBox.addItem(salle);
        }
    }


    // Getters
    public JComboBox<Film> getFilmComboBox() {
        return filmComboBox;
    }

    public JComboBox<Salle> getSalleComboBox() {
        return salleComboBox;
    }

    public JTextField getDateHeureTextField() {
        return dateHeureTextField;
    }

    public JTextField getVersionTextField() {
        return versionTextField;
    }

    public JTextField getTypeProjectionTextField() {
        return typeProjectionTextField;
    }

    public JTextField getPrixPlaceTextField() {
        return prixPlaceTextField;
    }


    public void viderFormulaire() {
        filmComboBox.setSelectedIndex(-1); // Déselectionner le ComboBox
        salleComboBox.setSelectedIndex(-1);
        dateHeureTextField.setText("");
        versionTextField.setText("");
        typeProjectionTextField.setText("");
        prixPlaceTextField.setText("");
    }


    public void remplirFormulaireAvecSeance(Seance seance) {
        filmComboBox.setSelectedItem(seance.getFilm());
        salleComboBox.setSelectedItem(seance.getSalle());
        dateHeureTextField.setText(seance.getDateHeure().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // Formater LocalDateTime en String
        versionTextField.setText(seance.getVersion());
        typeProjectionTextField.setText(seance.getTypeProjection());
        prixPlaceTextField.setText(String.valueOf(seance.getPrixPlace()));
    }
}