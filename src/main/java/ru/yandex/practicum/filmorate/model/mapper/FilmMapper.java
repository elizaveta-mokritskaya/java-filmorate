package ru.yandex.practicum.filmorate.model.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.request.FilmRequest;

import java.util.HashSet;

@Component
public class FilmMapper {
    public Film getFilm(FilmRequest request) {
        return Film.builder()
                .name(request.getName())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .likes(request.getLikes() == null ? new HashSet<>() : request.getLikes())
                .build();
    }
}
