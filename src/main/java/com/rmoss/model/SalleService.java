package com.rmoss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalleService {
    private List<Salle> salles = new ArrayList<>(); // Stockage en mémoire
private List<SalleServiceObserver> observers = new ArrayList<>(); // List of observers

    public SalleService() {
        // Constructeur par défaut
        // Initialisation de salles de test possible
    }

    public Salle ajouterSalle(Salle salle) {
        salle.setSalleId(UUID.randomUUID().toString()); // Générer un ID unique
        salles.add(salle);
        notifyObservers(); // Notify observers of change
        return salle;
    }

    public Salle modifierSalle(Salle salleModifie) {
        for (int i = 0; i < salles.size(); i++) {
            if (salles.get(i).getSalleId().equals(salleModifie.getSalleId())) {
                salles.set(i, salleModifie);
                notifyObservers(); // Notify observers of change
                return salleModifie;
            }
        }
        return null; // Salle non trouvée
    }

 public boolean supprimerSalle(String salleId) {
       boolean removed = salles.removeIf(salle -> salle.getSalleId().equals(salleId));
       if (removed) {
           notifyObservers(); // Notify observers of change
       }
       return removed;
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
// Observer Pattern Methods
   public void addObserver(SalleServiceObserver observer) {
       observers.add(observer);
   }
   public void removeObserver(SalleServiceObserver observer) {
       observers.remove(observer);
   }
   private void notifyObservers() {
       for (SalleServiceObserver observer : observers) {
           observer.onSallesChanged(); // Notify each observer
       }
   }
}