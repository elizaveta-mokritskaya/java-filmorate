package ru.yandex.practicum.filmorate.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.filmorate.model.validation.DoesntContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@ToString
public class UserRequest {
    @NotNull
    @Email
    private final String email;
    @NotNull
    @NotEmpty
    @DoesntContainWhitespaces
    private final String login;
    @NotNull
    private final String name;
    @NotNull
    @PastOrPresent
    private final Date birthday;
}
