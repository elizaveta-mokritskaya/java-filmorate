package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("GET all films request");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        log.info("GET film request by id: " + id);
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film addNewFilm(@Valid @RequestBody FilmRequest filmRequest) {
        log.info("POST add new film request: " + filmRequest);
        return filmService.addNewFilm(filmRequest);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT update film: " + film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        filmService.likeFilm(filmId, userId);
        return filmService.getFilmById(filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film unlikeFilm(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) {
        filmService.unlikeFilm(filmId, userId);
        return filmService.getFilmById(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(required = true, defaultValue = "10") Integer count) {
        return filmService.getTopFilms(count);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationExceptionHandle(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        log.info("Exception: " + exception.getMessage());
        throw exception;
    }
}
