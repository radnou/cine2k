package com.rmoss.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public MainFrame() {
        setTitle("Application de Cinéma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialiser le CardLayout et le JPanel principal
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout); // JPanel principal utilise CardLayout
        setContentPane(contentPanel); // Définir le JPanel principal comme contentPane de la JFrame

        // Initialiser les écrans de gestion (une seule fois ici !)
        ecranGestionFilms = new EcranGestionFilms();
        ecranGestionSalles = new EcranGestionSalles();
        ecranGestionSeances = new EcranGestionSeances();
        ecranReservations = new EcranReservations();

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

        // Au démarrage, afficher l'écran des films (par exemple, ou un écran d'accueil vide)
        cardLayout.show(contentPanel, "FILMS"); // Afficher l'écran des films au démarrage, ou mettez "FILMS" en "" pour un écran vide au départ.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}