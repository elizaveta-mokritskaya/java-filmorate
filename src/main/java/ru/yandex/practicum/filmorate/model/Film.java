package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
@Builder
public class Film {
    private Integer id;
    private String name;
    private String description;
    private Date releaseDate;
    private Duration duration;
}
