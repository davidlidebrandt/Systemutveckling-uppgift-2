package constraints;

import entities.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import java.time.LocalDate;
import java.util.Objects;

public class CategoryCheckValidator
        implements ConstraintValidator<CategoryCheck, String> {

    @Override
    public boolean isValid(
            String sentValue,
            ConstraintValidatorContext context) {
        for(Category val: Category.values()) {
            if(Objects.equals(val.toString().toLowerCase(), sentValue.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
