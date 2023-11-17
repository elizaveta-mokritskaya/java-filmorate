package ru.yandex.practicum.filmorate.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.request.UserRequest;

import java.util.HashSet;

@Component
public class UserMapper {
    public User getUser(UserRequest request) {
        String name = request.getName();
        if (name == null || name.isEmpty()) {
            name = request.getLogin();
        }
        return User.builder()
                .email(request.getEmail())
                .login(request.getLogin())
                .name(name)
                .birthday(request.getBirthday())
                .friends(request.getFriends() == null ? new HashSet<>() : request.getFriends())
                .build();
    }
}
