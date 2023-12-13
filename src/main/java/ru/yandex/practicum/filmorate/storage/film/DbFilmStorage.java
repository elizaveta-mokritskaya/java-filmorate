package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("databaseFilmStorage")
public class DbFilmStorage implements FilmStorage {
    public static final String INSERT_SQL = "Insert into " +
            "films(name, description, release_date, duration, rating_id) " +
            "values (?, ?, ?, ?, ?)";
    public static final String FILM_SELECT = "select " +
            "F.ID, F.NAME, F.DESCRIPTION, F.DURATION, F.RELEASE_DATE, M.ID as rating_id, M.NAME as rating_name " +
            "from films as F " +
            "join PUBLIC.MPAS M on M.ID = F.RATING_ID";
    public static final String SELECT_BY_ID_SQL = FILM_SELECT + " where F.ID = ?";
    public static final String UPDATE_SQL = "update films set " +
            "name=? " +
            ", description=? " +
            ", release_date=? " +
            ", duration=? " +
            ", rating_id=? " +
            "where id=?";
    public static final String DELETE_BY_ID_SQL = "delete from films where id = ?";
    public static final String INSERT_GENRES = "insert into film_genres(film_id, genre_id) values(?, ?)";
    public static final String DELETE_GENRES = "delete from film_genres where film_id = ?";
    public static final String SELECT_ALL = FILM_SELECT;

    private final JdbcTemplate jdbcTemplate;

    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getList() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToFilm);
    }

    @Override
    public Film add(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, new java.sql.Date( film.getReleaseDate().getTime()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        updateGenres(filmId, film.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        return Film.builder()
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .mpa(film.getMpa())
                .genres(film.getGenres())
                .id(filmId)
                .build();
    }

    @Override
    public Optional<Film> getById(int filmId) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, this::mapRowToFilm, filmId).stream().findFirst();
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update(UPDATE_SQL,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        updateGenres(film.getId(), film.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        return getById(film.getId()).get();
    }

    @Override
    public void deleteFilm(Film film) { jdbcTemplate.update(DELETE_BY_ID_SQL, film.getId()); }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        int filmId = resultSet.getInt("id");
        return Film.builder()
                .id(filmId)
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date"))
                .duration(resultSet.getInt("duration"))
                .mpa(MPA.builder()
                        .id(resultSet.getInt("rating_id"))
                        .name(resultSet.getString("rating_name"))
                        .build()
                )
                .build();
    }

    private void updateGenres(int filmId, List<Integer> genresIds) {
        jdbcTemplate.update(DELETE_GENRES, filmId);
        new HashSet<>(genresIds).forEach(
                genreId -> jdbcTemplate.update(INSERT_GENRES, filmId, genreId)
        );
    }

}
