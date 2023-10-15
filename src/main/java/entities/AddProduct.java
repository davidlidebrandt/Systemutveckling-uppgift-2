package entities;

import jakarta.validation.constraints.NotEmpty;

public class AddProduct {

    @NotEmpty(message = "No name provided")
    public String name;

    @NotEmpty(message = "No category provided")
    public String category;
}
