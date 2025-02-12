package com.rmoss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SeanceService {
    private List<Seance> seances = new ArrayList<>(); // Stockage en mémoire

    public SeanceService() {
        // Constructeur par défaut
        // Initialisation de séances de test possible ici
    }

    public Seance planifierSeance(Seance seance) {
        seance.setSeanceId(UUID.randomUUID().toString()); // Générer un ID unique
        seances.add(seance);
        return seance;
    }

    public Seance modifierSeance(Seance seanceModifie) {
        for (int i = 0; i < seances.size(); i++) {
            if (seances.get(i).getSeanceId().equals(seanceModifie.getSeanceId())) {
                seances.set(i, seanceModifie);
                return seanceModifie;
            }
        }
        return null; // Séance non trouvée
    }

    public boolean supprimerSeance(String seanceId) {
        return seances.removeIf(seance -> seance.getSeanceId().equals(seanceId));
    }

    public Seance getSeanceById(String seanceId) {
        for (Seance seance : seances) {
            if (seance.getSeanceId().equals(seanceId)) {
                return seance;
            }
        }
        return null; // Séance non trouvée
    }

    public List<Seance> getAllSeances() {
        return seances;
    }

    // Méthodes supplémentaires possibles (filtrer par film, par salle, par date, etc.)
    // ...

    public List<Seance> getSeancesByFilm(Film film) {
        List<Seance> seancesFilm = new ArrayList<>();
        for (Seance seance : seances) {
            if (seance.getFilm() != null && seance.getFilm().getFilmId().equals(film.getFilmId())) {
                seancesFilm.add(seance);
            }
        }
        return seancesFilm;
    }

    public List<Seance> getSeancesBySalle(Salle salle) {
        List<Seance> seancesSalle = new ArrayList<>();
        for (Seance seance : seances) {
            if (seance.getSalle() != null && seance.getSalle().getSalleId().equals(salle.getSalleId())) {
                seancesSalle.add(seance);
            }
        }
        return seancesSalle;
    }
}