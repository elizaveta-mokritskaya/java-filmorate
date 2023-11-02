package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateIsntBefore28december1895 {
    String message() default "Invalid date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
