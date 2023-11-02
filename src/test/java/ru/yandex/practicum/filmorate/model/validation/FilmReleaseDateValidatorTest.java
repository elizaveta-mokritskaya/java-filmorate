package ru.yandex.practicum.filmorate.model.validation;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class FilmReleaseDateValidatorTest {
    private final static FilmReleaseDateValidator validator = new FilmReleaseDateValidator();

    @Test
    void isValid_2023() {
        Date date = new GregorianCalendar(2023, 0, 1).getTime();
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1930() {
        Date date = new GregorianCalendar(1930, 0, 1).getTime();
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1896() {
        Date date = new GregorianCalendar(1896, 0, 1).getTime();
        assertTrue(validator.isValid(date, null));
    }

    @Test
    void isValid_1861() {
        Date date = new GregorianCalendar(1861, 0, 1).getTime();
        assertFalse(validator.isValid(date, null));
    }

    @Test
    void isValid_988() {
        Date date = new GregorianCalendar(988, 0, 1).getTime();
        assertFalse(validator.isValid(date, null));
    }
}