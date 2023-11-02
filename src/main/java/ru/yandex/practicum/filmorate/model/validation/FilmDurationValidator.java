package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class FilmDurationValidator implements
        ConstraintValidator<PositiveDuration, Duration> {

    @Override
    public void initialize(PositiveDuration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return !duration.isNegative();
    }
}
