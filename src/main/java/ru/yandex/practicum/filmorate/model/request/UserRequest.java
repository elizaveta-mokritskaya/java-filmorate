package ru.yandex.practicum.filmorate.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.filmorate.model.validation.DoesntContainWhitespaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

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
    private final String name;
    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;
}
