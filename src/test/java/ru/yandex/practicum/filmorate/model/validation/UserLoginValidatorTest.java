package ru.yandex.practicum.filmorate.model.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginValidatorTest {

    private final static UserLoginValidator validator = new UserLoginValidator();

    @Test
    void isValid_withoutWhitespaces() {
        String login = "login";
        assertTrue(validator.isValid(login, null));
    }

    @Test
    void isValid_withSpace() {
        String login = "log     in";
        assertFalse(validator.isValid(login, null));
    }

    @Test
    void isValid_withTab() {
        String login = "login   ";
        assertFalse(validator.isValid(login, null));
    }

    @Test
    void isValid_mixedSpaceAndLetters() {
        String login = "l    g  i           n";
        assertFalse(validator.isValid(login, null));
    }
}
