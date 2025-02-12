package com.rmoss.view;

import com.rmoss.model.Salle;

import javax.swing.*;
import java.awt.*;

public class FormulaireSalle extends JPanel {

    private JTextField nomTextField;
    private JTextField capaciteTextField;
    private JTextField nbRangeesTextField;
    private JTextField nbColonnesTextField;
    private JTextField typeSalleTextField;

    public FormulaireSalle() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Ligne 1: Nom
        JLabel nomLabel = new JLabel("Nom de la Salle:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(nomLabel, gbc);

        nomTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nomTextField, gbc);
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;

        // Ligne 2: Capacité
        JLabel capaciteLabel = new JLabel("Capacité:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(capaciteLabel, gbc);

        capaciteTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(capaciteTextField, gbc);

        // Ligne 3: Nombre de Rangées
        JLabel nbRangeesLabel = new JLabel("Nombre de Rangées:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(nbRangeesLabel, gbc);

        nbRangeesTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nbRangeesTextField, gbc);

        // Ligne 4: Nombre de Colonnes
        JLabel nbColonnesLabel = new JLabel("Nombre de Colonnes:");
        gbc.gridx = 0; gbc.gridy = 3;
        add(nbColonnesLabel, gbc);

        nbColonnesTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nbColonnesTextField, gbc);

        // Ligne 5: Type de Salle
        JLabel typeSalleLabel = new JLabel("Type de Salle:");
        gbc.gridx = 0; gbc.gridy = 4;
        add(typeSalleLabel, gbc);

        typeSalleTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typeSalleTextField, gbc);

        setBorder(BorderFactory.createTitledBorder("Informations de la Salle"));
    }

    // Getters
    public JTextField getNomTextField() {
        return nomTextField;
    }

    public JTextField getCapaciteTextField() {
        return capaciteTextField;
    }

    public JTextField getNbRangeesTextField() {
        return nbRangeesTextField;
    }

    public JTextField getNbColonnesTextField() {
        return nbColonnesTextField;
    }

    public JTextField getTypeSalleTextField() {
        return typeSalleTextField;
    }

    public void viderFormulaire() {
        nomTextField.setText("");
        capaciteTextField.setText("");
        nbRangeesTextField.setText("");
        nbColonnesTextField.setText("");
        typeSalleTextField.setText("");
    }

    public void remplirFormulaireAvecSalle(Salle salle) {
        nomTextField.setText(salle.getNom());
        capaciteTextField.setText(String.valueOf(salle.getCapacite()));
        nbRangeesTextField.setText(String.valueOf(salle.getNbRangees()));
        nbColonnesTextField.setText(String.valueOf(salle.getNbColonnes()));
        typeSalleTextField.setText(salle.getTypeSalle());
    }
}