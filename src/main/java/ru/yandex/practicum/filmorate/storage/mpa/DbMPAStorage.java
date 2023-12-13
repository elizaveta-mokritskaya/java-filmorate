package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class DbMPAStorage implements MPAStorage {
    public static final String SELECT_BY_ID = "select * from mpas where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public DbMPAStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getAllMPA() {
        return null;
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
}
