package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;
import java.util.GregorianCalendar;

public class FilmReleaseDateValidator implements
        ConstraintValidator<DateIsntBefore28december1895, Date> {

    private static final Date comparingDate = new GregorianCalendar(1895, 11, 28).getTime();

    @Override
    public void initialize(DateIsntBefore28december1895 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return !date.before(comparingDate);
    }
}
