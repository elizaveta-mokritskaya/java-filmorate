package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.DateIsntBefore28december1895;
import ru.yandex.practicum.filmorate.model.validation.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Date;

@Data
@Builder
public class Film {
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
    private Integer id;
}
