package ru.yandex.practicum.filmorate.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.yandex.practicum.filmorate.model.validation.DateIsntBefore28december1895;
import ru.yandex.practicum.filmorate.model.validation.MaxLength;
import ru.yandex.practicum.filmorate.model.validation.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FilmRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @MaxLength(val = 200)
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @DateIsntBefore28december1895
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @NotNull
    @PositiveDuration
    private Integer duration;
}
