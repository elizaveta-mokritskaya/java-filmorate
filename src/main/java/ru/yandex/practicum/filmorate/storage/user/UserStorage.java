package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    List<User> getList();

    User add(User user);

    Optional<User> getById(int userId);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> findUsersByIds(Set<Integer> setId);
}
