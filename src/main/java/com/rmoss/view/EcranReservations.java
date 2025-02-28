package com.rmoss.view;

import com.rmoss.controller.ReservationController;
import com.rmoss.model.Reservation;
import com.rmoss.model.ReservationService;
import com.rmoss.model.SeanceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EcranReservations extends JPanel {

    private ReservationService reservationService;
    private SeanceService seanceService; // Besoin du service des séances pour le controller et potentiellement la vue
    private ReservationController reservationController;

    private FormulaireReservation formulaireReservation;
    private JTable tableReservations;
    private DefaultTableModel tableModel;
    private JTextField champRecherche;
    private TableRowSorter<DefaultTableModel> sorter;

    private JButton boutonCreer;
    private JButton boutonModifier;
    private JButton boutonAnnuler; // "Annuler" au lieu de "Supprimer" pour les réservations
    private JButton boutonChargerPourModification;


    public EcranReservations(ReservationService reservationService, SeanceService seanceService) {
        this.reservationService = reservationService;
        this.seanceService = seanceService; // Initialisation du service des séances
        reservationController = new ReservationController(this.reservationService, this.seanceService, this); // Passer seanceService aussi

        setLayout(new BorderLayout());

        JLabel titreLabel = new JLabel("Gestion des Réservations");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titreLabel, BorderLayout.NORTH);

        // Panel Formulaire et Boutons
        JPanel formulaireEtBoutonsPanel = new JPanel(new BorderLayout());

        formulaireReservation = new FormulaireReservation();
        formulaireEtBoutonsPanel.add(formulaireReservation, BorderLayout.NORTH);

        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutonCreer = new JButton("Créer Réservation");
        boutonModifier = new JButton("Modifier");
        boutonAnnuler = new JButton("Annuler Réservation"); // "Annuler"
        boutonChargerPourModification = new JButton("Charger pour modification");

        boutonCreer.addActionListener(e -> reservationController.creerReservation());
        boutonModifier.addActionListener(e -> reservationController.modifierReservation());
        boutonAnnuler.addActionListener(e -> reservationController.annulerReservation()); // "annulerReservation"
        boutonChargerPourModification.addActionListener(e -> reservationController.chargerReservationPourModification());


        boutonsPanel.add(boutonCreer);
        boutonsPanel.add(boutonModifier);
        boutonsPanel.add(boutonAnnuler);
        boutonsPanel.add(boutonChargerPourModification);

        formulaireEtBoutonsPanel.add(boutonsPanel, BorderLayout.CENTER);

        add(formulaireEtBoutonsPanel, BorderLayout.WEST);

        // Panel Tableau et Recherche
        JPanel tableauEtRecherchePanel = new JPanel(new BorderLayout());

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel rechercheLabel = new JLabel("Rechercher une réservation:");
        champRecherche = new JTextField(20);
        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            private void filtrerTable() {
                String texteRecherche = champRecherche.getText();
                reservationController.rechercherReservations(texteRecherche);
            }
        });
        recherchePanel.add(rechercheLabel);
        recherchePanel.add(champRecherche);
        tableauEtRecherchePanel.add(recherchePanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Séance", "Client Nom", "Client Prénom", "Nombre de Places", "Type de Tarif", "Statut", "Date Réservation"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableReservations = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        tableReservations.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tableReservations);
        tableauEtRecherchePanel.add(scrollPane, BorderLayout.CENTER);

        add(tableauEtRecherchePanel, BorderLayout.CENTER);

        mettreAJourListeReservations();
    }


    public FormulaireReservation getFormulaireReservation() {
        return formulaireReservation;
    }

    public Reservation getReservationSelectionneeDansTable() {
        int indexLigneSelectionnee = tableReservations.getSelectedRow();
        if (indexLigneSelectionnee != -1) {
            int indexModele = tableReservations.convertRowIndexToModel(indexLigneSelectionnee);
            String reservationId = (String) tableModel.getValueAt(indexModele, 0);
            return reservationService.getReservationById(reservationId);
        }
        return null;
    }

    public void mettreAJourListeReservations() {
        if (reservationService == null) {
return;        }
        tableModel.setRowCount(0);
        List<Reservation> reservations = reservationService.getAllReservations();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        for (Reservation reservation : reservations) {
            tableModel.addRow(new Object[]{
                    reservation.getReservationId(),
                    reservation.getSeance(), // toString de Séance affiche Film et Date/Heure
                    reservation.getClientNom(),
                    reservation.getClientPrenom(),
                    reservation.getNbPlaces(),
                    reservation.getTypeTarif(),
                    reservation.getStatut(),
                    reservation.getDateReservation().format(dateTimeFormatter) // Formater la date de réservation
            });
        }
    }

    public void filtrerReservationsDansTable(String critere) {
        if (critere == null || critere.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + critere));
        }
    }
}