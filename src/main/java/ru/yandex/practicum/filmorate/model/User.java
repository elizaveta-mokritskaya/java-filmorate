package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.DoesntContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@Builder
public class User {
    private Integer id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @DoesntContainWhitespaces
    private String login;
    @NotNull
    private String name;
    @NotNull
    @PastOrPresent
    private Date birthday;
}

