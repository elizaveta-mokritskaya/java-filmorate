package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserDoesntExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.request.UserRequest;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {
    private final UserStorage storage;
    private final UserMapper mapper;
    private int id = 1;

    @Autowired
    public UserService(
            @Qualifier("databaseUserStorage")
            UserStorage storage,
            UserMapper mapper
    ) {
        this.storage = storage;
        this.mapper = mapper;
    }

    public List<User> getUsers() {
        return storage.getList();
    }

    public User getUserById(Integer id) {
        Optional<User> userOptional = storage.getUserById(id);
        if(userOptional.isEmpty()) {
            throw new UserDoesntExistException();
        }
        return storage.getUserById(id).get();
//        return storage.getList().stream().filter(user -> user.getId().equals(id)).findAny()
//                .orElseThrow(UserDoesntExistException::new);
    }

    public User addNewUser(User user) {
        if ((user.getEmail() == null) || (user.getEmail().isEmpty()) || (!user.getEmail().contains("@"))) {
            throw new ValidationException("в переданных данных электронная почта не может быть пустой и должна содержать символ @");
        }
        if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
//        if (user.getBirthday().after(LocalDate.now())) {
//            throw new ValidationException("дата рождения не может быть в будущем");
//        }
        if ((user.getName() == null) || (user.getName().isEmpty())) {
            user.setName(user.getLogin());
        } else if (storage.getUserByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("пользователь с указанным адресом электронной почты уже был добавлен ранее");
        }
        return storage.add(user);
    }

    public User updateUser(User user) {
        if (user == null) {
            throw new UserDoesntExistException();
        }
        if (user.getEmail().isEmpty()) {
            throw new ValidationException("в переданных данных отсутствует адрес электронной почты");
        }
        Optional<User> optionalUser = storage.getUserById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new UserDoesntExistException();
        }
        User userUpdate = optionalUser.get();
        userUpdate.setEmail(user.getEmail());
        userUpdate.setLogin(user.getLogin());
        if ((user.getName() == null) || (user.getName().isEmpty())) {
            user.setName(user.getLogin());
        }
        userUpdate.setName(user.getName());
        userUpdate.setBirthday(user.getBirthday());
        return storage.updateUser(user);
    }

//    public User addNewUser(UserRequest request) {
//        User user = mapper.getUser(request);
//        user.setId(id++);
//        storage.add(user);
//        return user;
//    }

    public void makeFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
//        user1.getFriends().add(id2);
//        user2.getFriends().add(id1);
    }

    public void deleteFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
//        user1.getFriends().remove(id2);
//        user2.getFriends().remove(id1);
    }

    public List<User> getFriends(Integer id) {
        User user = getUserById(id);
//        return user.getFriends().stream().map(this::getUserById).collect(Collectors.toList());
        return new ArrayList<>();
    }
    public List<User> getCommonFriends(Integer id1, Integer id2) {
        User user1 = getUserById(id1);
        User user2 = getUserById(id2);
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
