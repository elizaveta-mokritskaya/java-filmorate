package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.request.UserRequest;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        log.info("GET all users request");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUsersById(@PathVariable("id") Integer id) {
        log.info("GET user request by id: " + id);
        return userService.getUserById(id);
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("PUT update user: " + user);
        User currentUser = userService.getUserById(user.getId());
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setBirthday(user.getBirthday());
        return currentUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addNewFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.makeFriends(id, friendId);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.deleteFriends(id, friendId);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Integer id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationExceptionHandle(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        log.info("Exception: " + exception.getMessage());
        throw exception;
    }
}
