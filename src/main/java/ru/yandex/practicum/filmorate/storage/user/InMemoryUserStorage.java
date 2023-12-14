package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> getList() {
        return users;
    }

    @Override
    public User add(User user) {
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getById(int userId) {
        return Optional.empty();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public List<User> findUsersByIds(Set<Integer> setId) {
        return null;
    }
}
