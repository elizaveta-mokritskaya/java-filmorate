package ru.yandex.practicum.filmorate.model.validation;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmReleaseDateValidatorTest {
    private static final FilmReleaseDateValidator validator = new FilmReleaseDateValidator();

    @Test
    void isValid_2023() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1930() {
        LocalDate date = LocalDate.of(1930, 1, 1);
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1896() {
        LocalDate date = LocalDate.of(1896, 1, 1);
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1861() {
        LocalDate date = LocalDate.of(1861, 1, 1);
        assertFalse(validator.isValid(date, null));
    }

    @Test
    void isValid_988() {
        LocalDate date = LocalDate.of(988, 1, 1);
        assertFalse(validator.isValid(date, null));
    }
}