package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
public class DbGenreStorage implements GenreStorage{

    @Override
    public List<Genre> getAllGenre() {
        return null;
    }

    @Override
    public Genre getById(int genreId) {
        return null;
    }
}
