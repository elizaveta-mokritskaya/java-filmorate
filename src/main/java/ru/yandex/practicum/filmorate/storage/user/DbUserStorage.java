package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("databaseUserStorage")
public class DbUserStorage implements UserStorage {
    public static final String INSERT = "insert into " +
            "users(name, login, birthday, email) " +
            "values (?,    ?,     ? ,      ?)";
    public static final String SELECT_ALL = "select * from users";
    public static final String SELECT_BY_ID = "select * from users where id = ?";
    public static final String UPDATE = "update users set " +
            "name=? " +
            ", login=? " +
            ", birthday=? " +
            ", email=? " +
            "where id=?";
    public static final String DELETE_BY_ID = "delete from users where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getList() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToUser);
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT, new String[]{"id"});
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setDate(3, Date.valueOf(user.getBirthday()));
            statement.setString(4, user.getEmail());
            return statement;
        }, keyHolder);
        return User.builder()
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .id(Objects.requireNonNull(keyHolder.getKey()).intValue())
                .build();
    }

    @Override
    public Optional<User> getById(int userId) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapRowToUser, userId).stream().findFirst();
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE,
                user.getName(),
                user.getLogin(),
                Date.valueOf(user.getBirthday()),
                user.getEmail(),
                user.getId());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        jdbcTemplate.update(DELETE_BY_ID, user.getId());
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> findUsersByIds(Set<Integer> setId) {
        String selectByIdsSql = "select * from users where ID in (" +
                String.join(",", Collections.nCopies(setId.size(), "?"))
                + ") order by ID";
        return jdbcTemplate.query(selectByIdsSql, setId.toArray(), this::mapRowToUser);
    }
}
