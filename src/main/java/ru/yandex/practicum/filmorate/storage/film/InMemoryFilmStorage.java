package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();

    @Override
    public List<Film> getList() {
        return films;
    }

    @Override
    public Film add(Film film) {
        films.add(film);
        return film;
    }

    @Override
    public Optional<Film> getById(int filmId) {
        return Optional.empty();
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public void addLike(int filmId, int userId) {

    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {

    }
}
