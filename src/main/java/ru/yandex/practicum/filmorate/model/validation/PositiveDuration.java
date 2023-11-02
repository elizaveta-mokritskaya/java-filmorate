package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmDurationValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveDuration {
    String message() default "Negative duration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
