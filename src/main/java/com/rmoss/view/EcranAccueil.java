package com.rmoss.view;

import javax.swing.*;
import java.awt.*;

public class EcranAccueil extends JPanel {

    public EcranAccueil() {
        setLayout(new BorderLayout()); // Layout de base

        JLabel titreLabel = new JLabel("Bienvenue dans SmartCinema !");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrer le texte

        add(titreLabel, BorderLayout.CENTER); // Ajouter le label au centre

        // Vous pourrez ajouter d'autres composants pour le tableau de bord ici
        // par exemple, des statistiques rapides, des boutons d'accès rapide, etc.
    }
}