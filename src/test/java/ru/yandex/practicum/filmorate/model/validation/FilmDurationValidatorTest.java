package ru.yandex.practicum.filmorate.model.validation;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmDurationValidatorTest {

    private final static FilmDurationValidator validator = new FilmDurationValidator();

    @Test
    void isValid_positiveDuration() {
        assertTrue(validator.isValid(Duration.ZERO.plusHours(1), null));
        assertTrue(validator.isValid(Duration.ZERO.plusMillis(1), null));
        assertTrue(validator.isValid(Duration.ZERO.plusMinutes(1), null));
    }

    @Test
    void isValid_negativeDuration() {
        assertFalse(validator.isValid(Duration.ZERO.minusHours(1), null));
        assertFalse(validator.isValid(Duration.ZERO.minusMillis(1), null));
        assertFalse(validator.isValid(Duration.ZERO.minusMinutes(1), null));
    }

    @Test
    void isValid_zeroDuration() {
        assertTrue(validator.isValid(Duration.ZERO, null));
    }
}