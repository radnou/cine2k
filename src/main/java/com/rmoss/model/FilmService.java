package com.rmoss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // Pour générer des IDs uniques

public class FilmService {
    private List<Film> films = new ArrayList<>(); // Stockage en mémoire pour l'instant

    public FilmService() {
        // Constructeur par défaut
        // On pourrait initialiser des films de test ici si besoin
    }

    public Film ajouterFilm(Film film) {
        film.setFilmId(UUID.randomUUID().toString()); // Générer un ID unique UUID
        films.add(film);
        return film;
    }

    public Film modifierFilm(Film filmModifie) {
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getFilmId().equals(filmModifie.getFilmId())) {
                films.set(i, filmModifie);
                return filmModifie;
            }
        }
        return null; // Film non trouvé
    }

    public boolean supprimerFilm(String filmId) {
        return films.removeIf(film -> film.getFilmId().equals(filmId));
    }

    public Film getFilmById(String filmId) {
        for (Film film : films) {
            if (film.getFilmId().equals(filmId)) {
                return film;
            }
        }
        return null; // Film non trouvé
    }

    public List<Film> getAllFilms() {
        return films;
    }

}