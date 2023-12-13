package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreDoesntExistException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;

@Component
public class GenreService {

    private final GenreStorage storage;

    public GenreService(GenreStorage storage) {
        this.storage = storage;
    }

    public List<Genre> getAllGenre() {
        return storage.getAllGenre();
    }

    public Genre getGenreById(int genreId) {
        Optional<Genre> genresOptional = storage.getById(genreId);
        if (genresOptional.isEmpty()) {
            throw new GenreDoesntExistException();
        }
        return genresOptional.get();
    }
}
