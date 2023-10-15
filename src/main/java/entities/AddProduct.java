package entities;

import constraints.CategoryCheck;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotEmpty;

public class AddProduct {

    @NotEmpty(message = "No name provided")
    public String name;

    @CategoryCheck
    public String category;
}
