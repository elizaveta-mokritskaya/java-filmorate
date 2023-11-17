package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage storage;
    private final FilmMapper mapper;
    private final UserService userService;
    private int id = 1;

    public List<Film> getFilms() {
        return storage.getList();
    }

    public Film getFilmById(Integer id) {
        return storage.getList().stream().filter(film -> film.getId().equals(id)).findAny()
                .orElseThrow(FilmDoesntExistException::new);
    }

    public Film addNewFilm(FilmRequest request) {
        Film film = mapper.getFilm(request);
        film.getLikes().forEach(userService::checkIfUserExists);
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








