package constraints;

import entities.Category;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CategoryCheckValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface CategoryCheck {

    String message() default
            "Invalid category";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
