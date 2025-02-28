package com.rmoss.view;

import com.rmoss.model.Film;
import com.rmoss.model.FilmService;
import com.rmoss.model.FilmServiceObserver;
import com.rmoss.model.Salle;
import com.rmoss.model.SalleService;
import com.rmoss.model.SalleServiceObserver;
import com.rmoss.model.Seance;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FormulaireSeance extends JPanel implements FilmServiceObserver, SalleServiceObserver {


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
         //Adding the observer
        this.filmService.addObserver(this);
        this.salleService.addObserver(this);

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

    DefaultComboBoxModel<Film> model = new DefaultComboBoxModel<>();
    for (Film film : filmService.getAllFilms()){
        model.addElement(film);
    }
    filmComboBox.setModel(model);
    if (model.getSize() > 0) {
        filmComboBox.setSelectedIndex(0); // Optionally, select the first item
    }


}
   private void chargerSallesDansComboBox() {
    if (salleService == null) {
        return;
    }

    DefaultComboBoxModel<Salle> model = new DefaultComboBoxModel<>();
    for (Salle salle : salleService.getAllSalles()) {
        model.addElement(salle);
    }
    salleComboBox.setModel(model);
    if (model.getSize() > 0) {
        salleComboBox.setSelectedIndex(0);
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


    @Override
    public void onFilmsChanged() {
        chargerFilmsDansComboBox();
    }

    @Override
    public void onSallesChanged() {
    chargerSallesDansComboBox();
    }
}