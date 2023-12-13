package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class DbMPAStorage implements MPAStorage {
    public static final String SELECT_BY_ID = "select * from mpas where id = ?";
    public static final String SELECT_ALL = "select * from mpas";
    public static final String INSERT = "insert into mpas(name) values (?)";



    private final JdbcTemplate jdbcTemplate;

    public DbMPAStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getAllMPA() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToMPA);
    }

    @Override
    public Optional<MPA> getById(int mpaId) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapRowToMPA, mpaId).stream().findFirst();
    }

    private MPA mapRowToMPA(ResultSet resultSet, int rowNum) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    public MPA addMPA(MPA mpa) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement prepared = connection.prepareStatement(INSERT, new String[]{"id"});
            prepared.setString(1, mpa.getName());
            return prepared;
        }, keyHolder);
        return MPA.builder()
                .name(mpa.getName())
                .id(Objects.requireNonNull(keyHolder.getKey()).intValue())
                .build();
    }
}