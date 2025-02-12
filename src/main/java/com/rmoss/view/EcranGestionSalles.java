package com.rmoss.view;

import com.rmoss.controller.SalleController;
import com.rmoss.model.Salle;
import com.rmoss.model.SalleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class EcranGestionSalles extends JPanel {

    private SalleService salleService;
    private SalleController salleController;

    private FormulaireSalle formulaireSalle;
    private JTable tableSalles;
    private DefaultTableModel tableModel;
    private JTextField champRecherche;
    private TableRowSorter<DefaultTableModel> sorter;

    private JButton boutonAjouter;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonChargerPourModification;

    public EcranGestionSalles(SalleService salleService) {
        this.salleService = salleService;
        salleController = new SalleController(this.salleService, this);

        setLayout(new BorderLayout());

        JLabel titreLabel = new JLabel("Gestion des Salles");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titreLabel, BorderLayout.NORTH);

        // Panel Formulaire et Boutons
        JPanel formulaireEtBoutonsPanel = new JPanel(new BorderLayout());

        formulaireSalle = new FormulaireSalle();
        formulaireEtBoutonsPanel.add(formulaireSalle, BorderLayout.NORTH);

        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutonAjouter = new JButton("Ajouter");
        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");
        boutonChargerPourModification = new JButton("Charger pour modification");

        boutonAjouter.addActionListener(e -> salleController.ajouterSalle());
        boutonModifier.addActionListener(e -> salleController.modifierSalle());
        boutonSupprimer.addActionListener(e -> salleController.supprimerSalle());
        boutonChargerPourModification.addActionListener(e -> salleController.chargerSallePourModification());


        boutonsPanel.add(boutonAjouter);
        boutonsPanel.add(boutonModifier);
        boutonsPanel.add(boutonSupprimer);
        boutonsPanel.add(boutonChargerPourModification);

        formulaireEtBoutonsPanel.add(boutonsPanel, BorderLayout.CENTER);


        add(formulaireEtBoutonsPanel, BorderLayout.WEST);

        // Panel Tableau et Recherche
        JPanel tableauEtRecherchePanel = new JPanel(new BorderLayout());

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel rechercheLabel = new JLabel("Rechercher une salle:");
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
                salleController.rechercherSalles(texteRecherche);
            }
        });
        recherchePanel.add(rechercheLabel);
        recherchePanel.add(champRecherche);
        tableauEtRecherchePanel.add(recherchePanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nom", "Capacité", "Rangées", "Colonnes", "Type de Salle"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSalles = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        tableSalles.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tableSalles);
        tableauEtRecherchePanel.add(scrollPane, BorderLayout.CENTER);

        add(tableauEtRecherchePanel, BorderLayout.CENTER);

        mettreAJourListeSalles();
    }

    public FormulaireSalle getFormulaireSalle() {
        return formulaireSalle;
    }

    public Salle getSalleSelectionneeDansTable() {
        int indexLigneSelectionnee = tableSalles.getSelectedRow();
        if (indexLigneSelectionnee != -1) {
            int indexModele = tableSalles.convertRowIndexToModel(indexLigneSelectionnee);
            String salleId = (String) tableModel.getValueAt(indexModele, 0);
            return salleService.getSalleById(salleId);
        }
        return null;
    }

    public void mettreAJourListeSalles() {
        tableModel.setRowCount(0);
        List<Salle> salles = salleService.getAllSalles();

        for (Salle salle : salles) {
            tableModel.addRow(new Object[]{
                    salle.getSalleId(),
                    salle.getNom(),
                    salle.getCapacite(),
                    salle.getNbRangees(),
                    salle.getNbColonnes(),
                    salle.getTypeSalle()
            });
        }
    }

    public void filtrerSallesDansTable(String critere) {
        if (critere == null || critere.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + critere));
        }
    }
}