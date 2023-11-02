package ru.yandex.practicum.filmorate.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserLoginValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DoesntContainWhitespaces {
    String message() default "Filed contains whitespaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
