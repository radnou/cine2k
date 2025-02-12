package com.rmoss.view;

import com.rmoss.model.Reservation;
import com.rmoss.model.Seance;
import com.rmoss.model.SeanceService;

import javax.swing.*;
import java.awt.*;

public class FormulaireReservation extends JPanel {

    private JComboBox<Seance> seanceComboBox;
    private JTextField clientNomTextField;
    private JTextField clientPrenomTextField;
    private JTextField nbPlacesTextField;
    private JTextField typeTarifTextField;
    private JTextField statutTextField;

    private SeanceService seanceService;

    public FormulaireReservation() {
        seanceService = new SeanceService(); // Initialisation pour ComboBox Séance

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;


        // Ligne 1: Séance
        JLabel seanceLabel = new JLabel("Séance:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(seanceLabel, gbc);

        seanceComboBox = new JComboBox<>();
        chargerSeancesDansComboBox(); // Remplir ComboBox avec les Séances
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(seanceComboBox, gbc);


        // Ligne 2: Nom du Client
        JLabel clientNomLabel = new JLabel("Nom du Client:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(clientNomLabel, gbc);

        clientNomTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(clientNomTextField, gbc);

        // Ligne 3: Prénom du Client
        JLabel clientPrenomLabel = new JLabel("Prénom du Client:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(clientPrenomLabel, gbc);

        clientPrenomTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(clientPrenomTextField, gbc);

        // Ligne 4: Nombre de Places
        JLabel nbPlacesLabel = new JLabel("Nombre de Places:");
        gbc.gridx = 0; gbc.gridy = 3;
        add(nbPlacesLabel, gbc);

        nbPlacesTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nbPlacesTextField, gbc);

        // Ligne 5: Type de Tarif
        JLabel typeTarifLabel = new JLabel("Type de Tarif:");
        gbc.gridx = 0; gbc.gridy = 4;
        add(typeTarifLabel, gbc);

        typeTarifTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typeTarifTextField, gbc);

        // Ligne 6: Statut de la Réservation
        JLabel statutLabel = new JLabel("Statut de la Réservation:");
        gbc.gridx = 0; gbc.gridy = 5;
        add(statutLabel, gbc);

        statutTextField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(statutTextField, gbc);


        setBorder(BorderFactory.createTitledBorder("Informations de la Réservation"));
    }


    private void chargerSeancesDansComboBox() {
        seanceComboBox.removeAllItems();
        for (Seance seance : seanceService.getAllSeances()) {
            seanceComboBox.addItem(seance);
        }
    }


    // Getters
    public JComboBox<Seance> getSeanceComboBox() {
        return seanceComboBox;
    }

    public JTextField getClientNomTextField() {
        return clientNomTextField;
    }

    public JTextField getClientPrenomTextField() {
        return clientPrenomTextField;
    }

    public JTextField getNbPlacesTextField() {
        return nbPlacesTextField;
    }

    public JTextField getTypeTarifTextField() {
        return typeTarifTextField;
    }

    public JTextField getStatutTextField() {
        return statutTextField;
    }


    public void viderFormulaire() {
        seanceComboBox.setSelectedIndex(-1);
        clientNomTextField.setText("");
        clientPrenomTextField.setText("");
        nbPlacesTextField.setText("");
        typeTarifTextField.setText("");
        statutTextField.setText("");
    }


    public void remplirFormulaireAvecReservation(Reservation reservation) {
        seanceComboBox.setSelectedItem(reservation.getSeance());
        clientNomTextField.setText(reservation.getClientNom());
        clientPrenomTextField.setText(reservation.getClientPrenom());
        nbPlacesTextField.setText(String.valueOf(reservation.getNbPlaces()));
        typeTarifTextField.setText(reservation.getTypeTarif());
        statutTextField.setText(reservation.getStatut());
    }
}