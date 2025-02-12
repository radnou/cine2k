package com.rmoss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalleService {
    private List<Salle> salles = new ArrayList<>(); // Stockage en mémoire

    public SalleService() {
        // Constructeur par défaut
        // Initialisation de salles de test possible
    }

    public Salle ajouterSalle(Salle salle) {
        salle.setSalleId(UUID.randomUUID().toString()); // Générer un ID unique
        salles.add(salle);
        return salle;
    }

    public Salle modifierSalle(Salle salleModifie) {
        for (int i = 0; i < salles.size(); i++) {
            if (salles.get(i).getSalleId().equals(salleModifie.getSalleId())) {
                salles.set(i, salleModifie);
                return salleModifie;
            }
        }
        return null; // Salle non trouvée
    }

    public boolean supprimerSalle(String salleId) {
        return salles.removeIf(salle -> salle.getSalleId().equals(salleId));
    }

    public Salle getSalleById(String salleId) {
        for (Salle salle : salles) {
            if (salle.getSalleId().equals(salleId)) {
                return salle;
            }
        }
        return null; // Salle non trouvée
    }

    public List<Salle> getAllSalles() {
        return salles;
    }

    // Méthodes supplémentaires possibles (recherche par type de salle, etc.)
    // ...
}