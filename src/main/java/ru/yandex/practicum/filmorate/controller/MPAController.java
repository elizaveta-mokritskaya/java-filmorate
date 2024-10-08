package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpa")
public class MPAController {

    private final MPAService mpaService;

    @GetMapping
    public List<MPA> getAllMPA() {
        return mpaService.getAllMPA();
    }

    @GetMapping("/{mpaId}")
    public MPA getMPA(@PathVariable int mpaId) {
        return mpaService.getMPAById(mpaId);
    }
}
