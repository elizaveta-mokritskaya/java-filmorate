package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getList();

    Film add(Film film);

    Optional<Film> getById(int filmId);

    Film updateFilm(Film film);

    void deleteFilm(Film film);
}
