package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage storage;
    private final UserService userService;
    private int id = 1;

    public FilmService(
            @Qualifier("databaseFilmStorage")
            FilmStorage storage,
            UserService userService) {
        this.storage = storage;
        this.userService = userService;
    }

    public List<Film> getFilms() {
        return storage.getList();
    }

    public Film getFilmById(Integer id) {
        Optional<Film> optionalFilm = storage.getById(id);
        if (optionalFilm.isEmpty()) {
            throw new FilmDoesntExistException();
        }
        return optionalFilm.get();
    }

    public Film addNewFilm(Film film) {
        return storage.add(film);
    }


    public Set<Integer> likeFilm(Integer filmId, Integer userId) {
        User user = userService.getUserById(userId);
        Film film = getFilmById(filmId);
        if (film.getLikes().contains(user.getId())) {
            return film.getLikes();
        }
        storage.addLike(filmId, userId);
        return storage.getById(filmId).get().getLikes();
    }


    public void unlikeFilm(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        userService.checkIfUserExists(userId);
        User user = userService.getUserById(userId);
        storage.removeLike(filmId, userId);
    }

    public List<Film> getTopFilms(Integer count) {
        return storage.getList().stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }

    public Film updateFilm(Film film) {

        Optional<Film> optionalFilm = storage.getById(film.getId());
        if (optionalFilm.isEmpty()) {
            throw new FilmDoesntExistException();
        }
        Film currentFilm = optionalFilm.get();
        currentFilm.setName(film.getName());
        currentFilm.setDescription(film.getDescription());
        currentFilm.setReleaseDate(film.getReleaseDate());
        currentFilm.setDuration(film.getDuration());
        currentFilm.setLikes(film.getLikes());
        currentFilm.setMpa(film.getMpa());
        currentFilm.setGenres(film.getGenres());
        return storage.updateFilm(currentFilm);

    }
}
