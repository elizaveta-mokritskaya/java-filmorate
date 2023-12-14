package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.FriendStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DbFriendStorage implements FriendStorage {
    private static final String SELECT = "SELECT * FROM FRIENDS WHERE " +
            "(USER_ID = ?) AND (FRIEND_ID=?)";
    private static final String DELETE = "DELETE FROM FRIENDS WHERE " +
            "(USER_ID = ?) AND (FRIEND_ID=?)";
    private static final String INSERT =
            "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, FRIENDSHIP_STATUS) " +
                    "VALUES (           ?,          ?,          ?  )";
    public static final String SELECT_BY_ID = "SELECT * FROM FRIENDS where ID = ?";
    public static final String SELECT_BY_USER_ID = "SELECT * FROM FRIENDS where USER_ID = ?";
    private final JdbcTemplate jdbcTemplate;

    public DbFriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Friend> findFriend(int userId, int friendsId) {
        return jdbcTemplate.query(SELECT, this::mapRowToFriends, userId, friendsId).stream().findFirst();
    }

    @Override
    public Friend addFriend(Friend friend) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    INSERT,
                    new String[]{"id"}
            );
            statement.setInt(1, friend.getUserId());
            statement.setInt(2, friend.getFriendId());
            statement.setString(3, friend.getFriendStatus().name());
            return statement;
        }, keyHolder);
        return getById(keyHolder.getKey().intValue()).get();
    }

    @Override
    public List<Friend> getFriendByUserId(int userId) {
        return jdbcTemplate.query(
                SELECT_BY_USER_ID,
                this::mapRowToFriends, userId
        );
    }

    @Override
    public void deleteFriend(int userId, int friendsId) {
        jdbcTemplate.update(DELETE,userId, friendsId);
    }

    private Friend mapRowToFriends(ResultSet resultSet, int rowNum) throws SQLException {
        return Friend.builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .friendId(resultSet.getInt("friend_id"))
                .friendStatus(FriendStatus.valueOf(resultSet.getString("friendship_status")))
                .build();
    }

    private Optional<Friend> getById(int id) {
        return jdbcTemplate.query(
                SELECT_BY_ID,
                this::mapRowToFriends, id
        ).stream().findFirst();
    }
}
