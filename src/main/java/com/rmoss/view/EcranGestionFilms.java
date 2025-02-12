package com.rmoss.view;

import com.rmoss.controller.FilmController;
import com.rmoss.model.Film;
import com.rmoss.model.FilmService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EcranGestionFilms extends JPanel {

    private FilmService filmService; // Service pour manipuler les films (Model)
    private FilmController filmController; // Contrôleur associé à cet écran

    private FormulaireFilm formulaireFilm; // Formulaire de saisie/modification des films
    private JTable tableFilms; // Tableau pour afficher la liste des films
    private DefaultTableModel tableModel; // Modèle de données pour le tableau
    private JTextField champRecherche;
    private TableRowSorter<DefaultTableModel> sorter; // Pour le tri et le filtrage du tableau

    private JButton boutonAjouter;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonChargerPourModification;


    public EcranGestionFilms(FilmService filmService) {
        this.filmService =  filmService; // Initialisation du service (Model)
        filmController = new FilmController(this.filmService, this); // Initialisation du contrôleur (Controller), en passant le service et la vue courante

        setLayout(new BorderLayout());

        JLabel titreLabel = new JLabel("Gestion des Films");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titreLabel, BorderLayout.NORTH);

        // Panel pour le formulaire et les boutons d'action
        JPanel formulaireEtBoutonsPanel = new JPanel();
        formulaireEtBoutonsPanel.setLayout(new BorderLayout());

        // Formulaire de film
        formulaireFilm = new FormulaireFilm();
        formulaireEtBoutonsPanel.add(formulaireFilm, BorderLayout.NORTH); // Formulaire en haut

        // Panel pour les boutons d'action (Ajouter, Modifier, Supprimer, Charger pour modification)
        JPanel boutonsPanel = new JPanel();
        boutonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alignement à gauche des boutons
        boutonAjouter = new JButton("Ajouter");
        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");
        boutonChargerPourModification = new JButton("Charger pour modification");

        // Ajout des ActionListeners aux boutons (connexion Vue -> Controller)
        boutonAjouter.addActionListener(e -> filmController.ajouterFilm());
        boutonModifier.addActionListener(e -> filmController.modifierFilm());
        boutonSupprimer.addActionListener(e -> filmController.supprimerFilm());
        boutonChargerPourModification.addActionListener(e -> filmController.chargerFilmPourModification());


        boutonsPanel.add(boutonAjouter);
        boutonsPanel.add(boutonModifier);
        boutonsPanel.add(boutonSupprimer);
        boutonsPanel.add(boutonChargerPourModification);

        formulaireEtBoutonsPanel.add(boutonsPanel, BorderLayout.CENTER); // Boutons sous le formulaire


        add(formulaireEtBoutonsPanel, BorderLayout.WEST); // Formulaire et boutons à gauche

        // Panel pour le tableau des films et le champ de recherche
        JPanel tableauEtRecherchePanel = new JPanel();
        tableauEtRecherchePanel.setLayout(new BorderLayout());

        // Champ de recherche
        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel rechercheLabel = new JLabel("Rechercher un film:");
        champRecherche = new JTextField(20);
        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() { // Listener pour la recherche en temps réel
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrerTable(); }
            private void filtrerTable() {
                String texteRecherche = champRecherche.getText();
                filmController.rechercherFilms(texteRecherche); // Appel au contrôleur pour la recherche (qui pour l'instant filtre dans la vue)
            }
        });
        recherchePanel.add(rechercheLabel);
        recherchePanel.add(champRecherche);
        tableauEtRecherchePanel.add(recherchePanel, BorderLayout.NORTH);


        // Tableau des films
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Titre", "Réalisateur", "Genre", "Durée", "Date de Sortie", "Classification"}) { // Définition des colonnes
            @Override
            public boolean isCellEditable(int row, int column) { // Rendre le tableau non éditable directement
                return false;
            }
        };
        tableFilms = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel); // Initialisation du TableRowSorter avec le modèle
        tableFilms.setRowSorter(sorter); // Application du sorter au tableau

        JScrollPane scrollPane = new JScrollPane(tableFilms);
        tableauEtRecherchePanel.add(scrollPane, BorderLayout.CENTER); // Tableau au centre

        add(tableauEtRecherchePanel, BorderLayout.CENTER); // Tableau et recherche au centre/est


        mettreAJourListeFilms(); // Initialisation : afficher la liste des films au démarrage
    }

    public FormulaireFilm getFormulaireFilm() {
        return formulaireFilm;
    }

    public Film getFilmSelectionneDansTable() {
        int indexLigneSelectionnee = tableFilms.getSelectedRow();
        if (indexLigneSelectionnee != -1) {
            // Récupérer l'index du modèle (en tenant compte du tri et du filtrage)
            int indexModele = tableFilms.convertRowIndexToModel(indexLigneSelectionnee);
            String filmId = (String) tableModel.getValueAt(indexModele, 0); // ID est dans la colonne 0 (cachée dans cet exemple, mais présent dans le modèle)
            return filmService.getFilmById(filmId); // Récupérer le Film via le service à partir de l'ID
        }
        return null; // Aucun film sélectionné
    }


    public void mettreAJourListeFilms() {
        tableModel.setRowCount(0); // Vider le tableau
        List<Film> films = filmService.getAllFilms();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatter pour la date (à adapter au format de Date de Film)

        for (Film film : films) {
            tableModel.addRow(new Object[]{
                    film.getFilmId(), // ID - peut être utile de l'avoir même si non affiché dans le tableau final pour l'identification interne
                    film.getTitre(),
                    film.getRealisateur(),
                    film.getGenre(),
                    film.getDuree(),
                    film.getDateSortie() != null ? dateFormatter.format(film.getDateSortie().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()) : "", // Formatage de la date
                    film.getClassification()
            });
        }
    }

    public void filtrerFilmsDansTable(String critere) {
        if (critere == null || critere.trim().length() == 0) {
            sorter.setRowFilter(null); // Retirer le filtre
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + critere)); // (?i) pour case-insensitive
        }
    }
}