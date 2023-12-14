package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmDoesntExistException;
import ru.yandex.practicum.filmorate.exception.UserDoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.request.UserRequest;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserStorage storage;
    //private final UserMapper mapper;
    private int id = 1;

    public UserService(@Qualifier("databaseUserStorage") UserStorage storage) {
        this.storage = storage;
    }

    public List<User> getUsers() {
        return storage.getList();
    }

    public User getUserById(Integer id) {
        Optional<User> optionalUser = storage.getById(id);
        if (optionalUser.isEmpty()) {
            throw new UserDoesntExistException();
        }
        return optionalUser.get();
    }

    public User addNewUser(User user) {
        return storage.add(user);
    }

    public User updateUser(User user) {
        if ((!user.getName().isEmpty()) && (!user.getEmail().isEmpty())) {
            Optional<User> optionalUser = storage.getById(user.getId());
            if (optionalUser.isEmpty()) {
                throw new UserDoesntExistException();
            }
            User currentUser = optionalUser.get();
            currentUser.setEmail(user.getEmail());
            currentUser.setLogin(user.getLogin());
            if ((user.getName() == null) || (user.getName().isEmpty())) {
                user.setName(user.getLogin());
            }
            currentUser.setName(user.getName());
            currentUser.setBirthday(user.getBirthday());
            return storage.updateUser(user);
        }
        throw new ValidationException("не выполнены условия: название не может быть пустым;\n" +
                "    e-mail не может быть пустым;\n");
    }

    public void makeFriends(Integer id1, Integer id2) {
//        User user1 = getUserById(id1);
//        User user2 = getUserById(id2);
//        user1.getFriends().add(id2);
//        user2.getFriends().add(id1);
    }

    public void deleteFriends(Integer id1, Integer id2) {
//        User user1 = getUserById(id1);
//        User user2 = getUserById(id2);
//        user1.getFriends().remove(id2);
//        user2.getFriends().remove(id1);
    }

    public List<User> getFriends(Integer id) {
//        User user = getUserById(id);
//        return user.getFriends().stream().map(this::getUserById).collect(Collectors.toList());
        return new ArrayList<>();
    }

    public List<User> getCommonFriends(Integer id1, Integer id2) {
//        User user1 = getUserById(id1);
//        User user2 = getUserById(id2);
//        return user1.getFriends().stream()
//                .filter(id -> user2.getFriends().contains(id))
//                .map(this::getUserById)
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }

    public void checkIfUserExists(Integer userId) {
        storage.getList().stream().filter(user -> Objects.equals(user.getId(), userId)).findAny()
                .orElseThrow(UserDoesntExistException::new);
    }
}
