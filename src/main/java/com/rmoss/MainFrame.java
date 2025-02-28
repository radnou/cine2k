package com.rmoss;

import com.rmoss.model.ReservationService;
import com.rmoss.model.Film;
import com.rmoss.model.FilmService;
import com.rmoss.model.Reservation;
import com.rmoss.model.Salle;
import com.rmoss.model.SalleService;
import com.rmoss.model.Seance;
import com.rmoss.model.SeanceService;
import com.rmoss.view.EcranGestionFilms;
import com.rmoss.view.EcranGestionSalles;
import com.rmoss.view.EcranGestionSeances;
import com.rmoss.view.EcranReservations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuGestion;
    private JMenuItem menuFilms;
    private JMenuItem menuSalles;
    private JMenuItem menuSeances;
    private JMenuItem menuReservations;
    private JPanel contentPanel; // Panel principal qui contiendra les écrans
    private CardLayout cardLayout; // Gestionnaire de disposition pour le panel principal

    // Déclarer les écrans de gestion comme attributs de la classe, pour qu'ils soient créés une seule fois
    private EcranGestionFilms ecranGestionFilms;
    private EcranGestionSalles ecranGestionSalles;
    private EcranGestionSeances ecranGestionSeances;
    private EcranReservations ecranReservations;

    // Services - il faut s'assurer qu'ils sont initialisés dans MainFrame pour pouvoir les utiliser ici
    private FilmService filmService = new FilmService();
    private SalleService salleService = new SalleService();
    private SeanceService seanceService = new SeanceService();
    private ReservationService reservationService = new ReservationService();

    public MainFrame() {
        setTitle("Application de Cinéma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 1024);
        setLocationRelativeTo(null);

        // Initialiser le CardLayout et le JPanel principal
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout); // JPanel principal utilise CardLayout
        setContentPane(contentPanel); // Définir le JPanel principal comme contentPane de la JFrame

        // Initialiser les écrans de gestion (une seule fois ici !)
        // Ajouter les jeux de données avant d'afficher l'écran initial
        ajouterJeuxDeDonnees();
        // **PASSAGE DES SERVICES AUX CONSTRUCTEURS DES ÉCRANS**
        ecranGestionFilms = new EcranGestionFilms(filmService); // Passer filmService
        ecranGestionSalles = new EcranGestionSalles(salleService); // Passer salleService
        ecranGestionSeances = new EcranGestionSeances(seanceService, filmService, salleService); // Passer seanceService, filmService, salleService
        ecranReservations = new EcranReservations(reservationService, seanceService); // Passer reservationService, seanceService


        // Ajouter les écrans au contentPanel avec CardLayout et leur donner un nom (pour les identifier)
        contentPanel.add(ecranGestionFilms, "FILMS"); // Nommez chaque carte, ici "FILMS"
        contentPanel.add(ecranGestionSalles, "SALLES"); // "SALLES"
        contentPanel.add(ecranGestionSeances, "SEANCES"); // "SEANCES"
        contentPanel.add(ecranReservations, "RESERVATIONS"); // "RESERVATIONS"


        menuBar = new JMenuBar();

        menuGestion = new JMenu("Gestion");
        menuBar.add(menuGestion);

        menuFilms = new JMenuItem("Films");
        menuFilms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher l'écran des films en utilisant CardLayout, pas besoin de tout recréer
                cardLayout.show(contentPanel, "FILMS"); // Afficher la carte nommée "FILMS"
            }
        });
        menuGestion.add(menuFilms);

        menuSalles = new JMenuItem("Salles");
        menuSalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher l'écran des salles en utilisant CardLayout
                cardLayout.show(contentPanel, "SALLES"); // Afficher la carte "SALLES"
            }
        });
        menuGestion.add(menuSalles);

        menuSeances = new JMenuItem("Séances");
        menuSeances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher l'écran des séances
                        ecranGestionSeances.mettreAJourListeSeances();
                cardLayout.show(contentPanel, "SEANCES"); // Afficher la carte "SEANCES"
            }
        });
        menuGestion.add(menuSeances);

        menuReservations = new JMenuItem("Réservations");
        menuReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher l'écran des réservations
                cardLayout.show(contentPanel, "RESERVATIONS"); // Afficher la carte "RESERVATIONS"
            }
        });
        menuGestion.add(menuReservations);

        setJMenuBar(menuBar);

        // **METTRE À JOUR LES VUES APRÈS L'AJOUT DES DONNÉES !**
        ecranGestionFilms.mettreAJourListeFilms();
        ecranGestionSalles.mettreAJourListeSalles();
        ecranGestionSeances.mettreAJourListeSeances();
        ecranReservations.mettreAJourListeReservations();

        // Au démarrage, afficher l'écran des films (par exemple, ou un écran d'accueil vide)
        cardLayout.show(contentPanel, "FILMS"); // Afficher l'écran des films au démarrage, ou mettez "FILMS" en "" pour un écran vide au départ.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    private void ajouterJeuxDeDonnees() {
        // **Films**
        Film film1 = new Film();
        film1.setTitre("Avatar: La Voie de l'Eau");
        film1.setRealisateur("James Cameron");
        film1.setGenre("Science-Fiction");
        film1.setDuree(192);
        film1.setDateSortie(new Date(123, 11, 14)); // Date pour 2022-12-14 (Année - 1900, Mois - 1, Jour)
        film1.setClassification("PG-13");
        filmService.ajouterFilm(film1);

        Film film2 = new Film();
        film2.setTitre("Oppenheimer");
        film2.setRealisateur("Christopher Nolan");
        film2.setGenre("Biopic, Drame");
        film2.setDuree(180);
        film2.setDateSortie(new Date(2023, 6, 21)); // Date pour 2023-07-21
        film2.setClassification("R");
        filmService.ajouterFilm(film2);

        Film film3 = new Film();
        film3.setTitre("Barbie");
        film3.setRealisateur("Greta Gerwig");
        film3.setGenre("Comédie, Fantastique");
        film3.setDuree(114);
        film3.setDateSortie(new Date(123, 6, 21)); // Date pour 2023-07-21
        film3.setClassification("PG-13");
        filmService.ajouterFilm(film3);

        // **Salles**
        Salle salle1 = new Salle();
        salle1.setNom("Salle 1");
        salle1.setCapacite(150);
        salle1.setTypeSalle("Standard");
        salleService.ajouterSalle(salle1);

        Salle salle2 = new Salle();
        salle2.setNom("Salle IMAX");
        salle2.setCapacite(250);
        salle2.setTypeSalle("IMAX");
        salleService.ajouterSalle(salle2);

        Salle salle3 = new Salle();
        salle3.setNom("Salle VIP");
        salle3.setCapacite(50);
        salle3.setTypeSalle("VIP");
        salleService.ajouterSalle(salle3);

        // **Séances**
        Seance seance1 = new Seance();
        seance1.setFilm(film1);
        seance1.setSalle(salle1);
        seance1.setDateHeure(LocalDateTime.of(123, Month.MARCH, 10, 14, 0)); // 2024-03-10 14:00
        seance1.setVersion("VF");
        seance1.setTypeProjection("2D");
        seance1.setPrixPlace(12.50);
        seanceService.planifierSeance(seance1);

        Seance seance2 = new Seance();
        seance2.setFilm(film2);
        seance2.setSalle(salle2);
        seance2.setDateHeure(LocalDateTime.of(123, Month.MARCH, 11, 19, 30)); // 2024-03-11 19:30
        seance2.setVersion("VOSTFR");
        seance2.setTypeProjection("IMAX 3D");
        seance2.setPrixPlace(18.00);
        seanceService.planifierSeance(seance2);

        Seance seance3 = new Seance();
        seance3.setFilm(film3);
        seance3.setSalle(salle3);
        seance3.setDateHeure(LocalDateTime.of(123, Month.MARCH, 12, 21, 0)); // 2024-03-12 21:00
        seance3.setVersion("VF");
        seance3.setTypeProjection("2D VIP");
        seance3.setPrixPlace(25.00);
        seanceService.planifierSeance(seance3);

        // **Réservations**
        Reservation reservation1 = new Reservation();
        reservation1.setSeance(seance1);
        reservation1.setClientNom("Dupont");
        reservation1.setClientPrenom("Jean");
        reservation1.setNbPlaces(2);
        reservation1.setTypeTarif("Plein Tarif");
        reservation1.setDateReservation(LocalDateTime.of(2023, Month.MARCH, 12, 21, 0));
        reservationService.creerReservation(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setSeance(seance2);
        reservation2.setClientNom("Martin");
        reservation2.setClientPrenom("Sophie");
        reservation2.setNbPlaces(1);
        reservation2.setTypeTarif("Tarif Réduit");
        reservation2.setDateReservation(LocalDateTime.of(2023, Month.MARCH, 12, 21, 0));

        reservationService.creerReservation(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setSeance(seance3);
        reservation3.setClientNom("Lefevre");
        reservation3.setClientPrenom("Pierre");
        reservation3.setNbPlaces(3);
        reservation3.setDateReservation(LocalDateTime.of(2023, Month.MARCH, 12, 21, 0));

        reservation3.setTypeTarif("Plein Tarif");
        reservationService.creerReservation(reservation3);

        System.out.println("Jeux de données ajoutés avec succès !"); // Confirmation dans la console
    }
}