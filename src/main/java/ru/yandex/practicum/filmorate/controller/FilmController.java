package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final List<Film> films;

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("GET all films request");
        return films;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        log.info("GET film request by id: " + id);
        return films.stream().filter(film -> film.getId().equals(id)).findAny()
                .orElseThrow(FilmDoesntExistException::new);
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody FilmRequest filmRequest) {
        log.info("POST add new film request: " + filmRequest);
        Film film = Film.builder()
                .id(films.stream().max(Comparator.comparingInt(Film::getId)).map(Film::getId).orElse(0) + 1)
                .name(filmRequest.getName())
                .description(filmRequest.getDescription())
                .releaseDate(filmRequest.getReleaseDate())
                .duration(filmRequest.getDuration())
                .build();
        films.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT update film: " + film);
        films.removeIf(f -> f.getId().equals(film.getId()));
        films.add(film);
        return film;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationExceptionHandle(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        log.info("Exception: " + exception.getMessage());
        throw exception;
    }
}
