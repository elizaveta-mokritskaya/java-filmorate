package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserDoesntExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.request.UserRequest;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final List<User> users;

    @GetMapping
    public List<User> getAllUsers() {
        log.info("GET all users request");
        return users;
    }

    @GetMapping("/{id}")
    public User getUsersById(@PathVariable("id") Integer id) {
        log.info("GET user request by id: " + id);
        return users.stream().filter(user -> user.getId().equals(id)).findAny()
                .orElseThrow(UserDoesntExistException::new);
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("POST add new user request: " + userRequest);
        String name = userRequest.getName();
        if (name == null || name.isEmpty()) {
            name = userRequest.getLogin();
        }
        User user = User.builder()
                .id(users.stream().max(Comparator.comparingInt(User::getId)).map(User::getId).orElse(0) + 1)
                .email(userRequest.getEmail())
                .login(userRequest.getLogin())
                .name(name)
                .birthday(userRequest.getBirthday())
                .build();
        users.add(user);
        return  user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserRequest userRequest) {
        log.info("PUT update user by id: " + id + ", " + userRequest);
        User currentUser = getUsersById(id);
        currentUser.setEmail(userRequest.getEmail());
        currentUser.setName(userRequest.getName());
        currentUser.setLogin(userRequest.getLogin());
        currentUser.setBirthday(userRequest.getBirthday());
        return  currentUser;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationExceptionHandle(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        log.info("Exception: " + exception.getMessage());
        throw exception;
    }
}
