package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.validation.DateIsntBefore28december1895;
import ru.yandex.practicum.filmorate.model.validation.MaxLength;
import ru.yandex.practicum.filmorate.model.validation.PositiveDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private Date releaseDate;
    @NotNull
    @PositiveDuration
    private Integer duration;

    private HashSet<Integer> likes = new HashSet<>();

    private List<Genre> genres = new ArrayList<>();

    private MPA mpa;

    public Film(Integer id, String name, String description, Date releaseDate, Integer duration, HashSet<Integer> likes,
                List<Genre> genres, MPA mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (genres != null) {
            this.genres = genres;
        }
        this.mpa = mpa;
    }

//    public Map<String, Objects> toMap() {
//        Map<String, Object> values = new HashMap<>();
//        values.put("name", name);
//        values.put("description", description);
//        values.put("release_Date", releaseDate);
//        values.put("duration", duration);
//        values.put("rating_id", mpa.getId());
//        return values;
//    }
}
