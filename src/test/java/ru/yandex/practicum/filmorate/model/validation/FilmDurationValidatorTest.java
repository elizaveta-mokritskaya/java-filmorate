package ru.yandex.practicum.filmorate.model.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmDurationValidatorTest {

    private static final FilmDurationValidator validator = new FilmDurationValidator();

    @Test
    void isValid_positiveDuration() {
        assertTrue(validator.isValid(1, null));
        assertTrue(validator.isValid(60, null));
        assertTrue(validator.isValid(600, null));
    }

    @Test
    void isValid_negativeDuration() {
        assertFalse(validator.isValid(-1, null));
        assertFalse(validator.isValid(-60, null));
        assertFalse(validator.isValid(-600, null));
    }

    @Test
    void isValid_zeroDuration() {
        assertTrue(validator.isValid(0, null));
    }
}