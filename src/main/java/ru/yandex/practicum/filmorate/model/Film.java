package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.DateIsntBefore28december1895;
import ru.yandex.practicum.filmorate.model.validation.MaxLength;
import ru.yandex.practicum.filmorate.model.validation.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Data
@Builder
public class Film {
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    @NotEmpty
    @MaxLength(val = 200)
    private String description;
    @NotNull
    @DateIsntBefore28december1895
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @NotNull
    @PositiveDuration
    private Integer duration;

    private Set<Integer> likes = new HashSet<>();

    private List<Genre> genres = new ArrayList<>();

    private MPA mpa;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Set<Integer> likes,
                List<Genre> genres, MPA mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (likes != null) {
            this.likes = likes;
        }
        if (genres != null) {
            this.genres = genres;
        }
        this.mpa = mpa;
    }
}
