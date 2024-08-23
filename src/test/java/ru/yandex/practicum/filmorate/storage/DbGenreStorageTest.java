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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
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
        Optional<Genre> genre = storage.getById(1);
        assertEquals("Комедия", genre.get().getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 2")
    public void getGenreById2() {
        Optional<Genre> genre = storage.getById(2);
        assertEquals("Драма", genre.get().getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 3")
    public void getGenreById3() {
        Optional<Genre> genre = storage.getById(3);
        assertEquals("Мультфильм", genre.get().getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 4")
    public void getGenreById4() {
        Optional<Genre> genre = storage.getById(4);
        assertEquals("Триллер", genre.get().getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 5")
    public void getGenreById5() {
        Optional<Genre> genre = storage.getById(5);
        assertEquals("Документальный", genre.get().getName());
    }

    @Test
    @DisplayName("Возвращает жанр с id 6")
    public void getGenreById6() {
        Optional<Genre> genre = storage.getById(6);
        assertEquals("Боевик", genre.get().getName());
    }
}
