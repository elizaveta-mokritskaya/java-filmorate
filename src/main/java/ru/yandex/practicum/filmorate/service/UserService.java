package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserDoesntExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.request.UserRequest;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage storage;
    private final UserMapper mapper;
    private int id = 1;

    public List<User> getUsers() {
        return storage.getList();
    }

    public User getUserById(Integer id) {
        return storage.getList().stream().filter(user -> user.getId().equals(id)).findAny()
                .orElseThrow(UserDoesntExistException::new);
    }

    public User addNewUser(UserRequest request) {
        User user = mapper.getUser(request);
        user.getFriends().forEach(this::checkIfUserExists);
        user.setId(id++);
        storage.add(user);
        return user;
    }

    public void makeFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
        user1.getFriends().add(id2);
        user2.getFriends().add(id1);
    }

    public void deleteFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
        user1.getFriends().remove(id2);
        user2.getFriends().remove(id1);
    }

    public List<User> getFriends(Integer id) {
        User user = getUserById(id);
        return user.getFriends().stream().map(this::getUserById).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
        return user1.getFriends().stream()
                .filter(id -> user2.getFriends().contains(id))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public void checkIfUserExists(Integer userId) {
        storage.getList().stream().filter(user -> Objects.equals(user.getId(), userId)).findAny()
                .orElseThrow(UserDoesntExistException::new);
    }
}
