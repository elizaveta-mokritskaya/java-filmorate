package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringLengthValidator implements ConstraintValidator<MaxLength, String> {
    private int val;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        val = constraintAnnotation.val();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int a = 0;
        return value.length() <= val;
    }
}
