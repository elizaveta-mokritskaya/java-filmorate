package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

public class FilmReleaseDateValidator implements
        ConstraintValidator<DateIsntBefore28december1895, LocalDate> {

    private static final LocalDate comparingDate = LocalDate.of(1895, 11, 28);

    @Override
    public void initialize(DateIsntBefore28december1895 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return !date.isBefore(comparingDate);
    }
}
