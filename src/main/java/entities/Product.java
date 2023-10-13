package entities;

import java.time.LocalDate;
import java.util.UUID;

public class Product {

    private String id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public Product(String name, Category category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.rating = 0;
        this.createdAt = LocalDate.now();
        this.lastModifiedAt = createdAt;
        if (name == null || id == null || category == null || name.trim().length() < 2) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", category=" + category + ", rating=" + rating + ", createdAt="
                + createdAt + ", lastModifiedAt=" + lastModifiedAt + "]";
    }

    public Product(String id, String name, Category category, int rating, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdAt = createdAt;
        this.lastModifiedAt = LocalDate.now();
        if (name == null || id == null || category == null || name.trim().length() < 2) {
            throw new IllegalArgumentException();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getLastModifiedAt() {
        return lastModifiedAt;
    }

}
