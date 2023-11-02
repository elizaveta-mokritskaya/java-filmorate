package ru.yandex.practicum.filmorate.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.yandex.practicum.filmorate.model.validation.DateIsntBefore28december1895;
import ru.yandex.practicum.filmorate.model.validation.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@ToString
public class FilmRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 200)
    private final String name;
    @NotNull
    @NotEmpty
    private final String description;
    @NotNull
    @DateIsntBefore28december1895
    private final Date releaseDate;
    @NotNull
    @PositiveDuration
    private final Duration duration;
}