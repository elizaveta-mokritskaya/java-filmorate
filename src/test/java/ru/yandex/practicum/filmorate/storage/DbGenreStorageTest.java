package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.DbGenreStorage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@RequiredArgsConstructor(onConstructor = @Autowired)
class DbGenreStorageTest {

    private static final String COMEDY = "Комедия";
    private static final String DRAMA = "Драма";
    private static final String CARTOON = "Мультфильм";
    private static final String THRILLER = "Триллер";
    private static final String DOCUMENTARY = "Документальный";
    private static final String ACTION_MOVIE = "Боевик";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private DbGenreStorage storage;

    @BeforeEach
    public void createStorage() {
        storage = new DbGenreStorage(jdbcTemplate);
    }

    @Test
    @DisplayName("Возвращает жанр с id 1")
    public void getGenreById1() {
        Genre genre = storage.getById(1);
        assertEquals("Комедия", genre.getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 2")
    public void getGenreById1() {
        Optional<Genre> genre = storage.getById(2);
        assertEquals("Драма", genre.get().getName());
    }
}
