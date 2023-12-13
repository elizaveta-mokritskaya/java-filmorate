package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage storage;
    private final FilmMapper mapper;
    private final UserService userService;
    private int id = 1;

    public FilmService(
            @Qualifier("databaseFilmStorage")
            FilmStorage storage,
            FilmMapper mapper,
            UserService userService) {
        this.storage = storage;
        this.mapper = mapper;
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

    public Film addNewFilm(FilmRequest request) {
        Film film = mapper.getFilm(request);
        film.setId(id++);
        storage.add(film);
        return film;
    }

    public void likeFilm(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        userService.checkIfUserExists(userId);
        film.getLikes().add(userId);
    }

    public void unlikeFilm(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        userService.checkIfUserExists(userId);
        film.getLikes().remove(userId);
    }

    public List<Film> getTopFilms(Integer count) {
        return storage.getList().stream()
                .sorted(Comparator.comparingInt(film -> - film.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }
}
