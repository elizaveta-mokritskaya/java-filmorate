package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendStorage {
    Optional<Friend> findFriend(int userId, int friendsId);

    Friend addFriend(Friend friend);

    List<Friend> getFriendByUserId(int userId);

    void deleteFriend(int userId, int friendsId);
}
