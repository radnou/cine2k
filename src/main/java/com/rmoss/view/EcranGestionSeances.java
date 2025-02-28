package com.rmoss.view;


import com.rmoss.controller.SeanceController;
import com.rmoss.model.Seance;
import com.rmoss.model.SeanceService;
import com.rmoss.model.FilmService;
import com.rmoss.model.SalleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EcranGestionSeances extends JPanel {

    private SeanceService seanceService;
    private FilmService filmService;
    private SalleService salleService;
    private SeanceController seanceController;

    private FormulaireSeance formulaireSeance;
    private JTable tableSeances;
    private DefaultTableModel tableModel;
    private JTextField champRecherche;
    private TableRowSorter<DefaultTableModel> sorter;

    private JButton boutonPlanifier;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonChargerPourModification;


    public EcranGestionSeances(SeanceService seanceService, FilmService filmService, SalleService salleService) {
        this.seanceService = seanceService;
        this.filmService = filmService; // Initialiser les services dont SeanceController pourrait avoir besoin
        this.salleService = salleService;
        this.seanceController = new SeanceController(this.seanceService, this);

        setLayout(new BorderLayout());

        JLabel titreLabel = new JLabel("Gestion des Séances");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titreLabel, BorderLayout.NORTH);

        // Panel Formulaire et Boutons
        JPanel formulaireEtBoutonsPanel = new JPanel(new BorderLayout());

        formulaireSeance = new FormulaireSeance(this.filmService, this.salleService);
        formulaireEtBoutonsPanel.add(formulaireSeance, BorderLayout.NORTH);

        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutonPlanifier = new JButton("Planifier Séance");
        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");
        boutonChargerPourModification = new JButton("Charger pour modification");


        boutonPlanifier.addActionListener(e -> seanceController.planifierSeance());
        boutonModifier.addActionListener(e -> seanceController.modifierSeance());
        boutonSupprimer.addActionListener(e -> seanceController.supprimerSeance());
        boutonChargerPourModification.addActionListener(e -> seanceController.chargerSeancePourModification());


        boutonsPanel.add(boutonPlanifier);
        boutonsPanel.add(boutonModifier);
        boutonsPanel.add(boutonSupprimer);
        boutonsPanel.add(boutonChargerPourModification);

        formulaireEtBoutonsPanel.add(boutonsPanel, BorderLayout.CENTER);

        add(formulaireEtBoutonsPanel, BorderLayout.WEST);

        // Panel Tableau et Recherche
        JPanel tableauEtRecherchePanel = new JPanel(new BorderLayout());

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel rechercheLabel = new JLabel("Rechercher une séance:");
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
                seanceController.rechercherSeances(texteRecherche);
            }
        });
        recherchePanel.add(rechercheLabel);
        recherchePanel.add(champRecherche);
        tableauEtRecherchePanel.add(recherchePanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Film", "Salle", "Date et Heure", "Version", "Type de Projection", "Prix"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSeances = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        tableSeances.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tableSeances);
        tableauEtRecherchePanel.add(scrollPane, BorderLayout.CENTER);

        add(tableauEtRecherchePanel, BorderLayout.CENTER);

        mettreAJourListeSeances();
    }


    public FormulaireSeance getFormulaireSeance() {
        return formulaireSeance;
    }

    public Seance getSeanceSelectionneeDansTable() {
        int indexLigneSelectionnee = tableSeances.getSelectedRow();
        if (indexLigneSelectionnee != -1) {
            int indexModele = tableSeances.convertRowIndexToModel(indexLigneSelectionnee);
            String seanceId = (String) tableModel.getValueAt(indexModele, 0);
            return seanceService.getSeanceById(seanceId);
        }
        return null;
    }

    public void mettreAJourListeSeances() {
        tableModel.setRowCount(0);
        if (seanceService == null) {
            return;
        }
        List<Seance> seances = seanceService.getAllSeances();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        for (Seance seance : seances) {
            tableModel.addRow(new Object[]{
                    seance.getSeanceId(),
                    seance.getFilm(), // toString de Film affiche le titre
                    seance.getSalle(), // toString de Salle affiche le nom
                    seance.getDateHeure().format(dateTimeFormatter), // Formater date et heure pour affichage
                    seance.getVersion(),
                    seance.getTypeProjection(),
                    seance.getPrixPlace()
            });
        }

    }

    public void filtrerSeancesDansTable(String critere) {
        if (critere == null || critere.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + critere));
        }
    }
}