package entities;

import java.time.LocalDate;

public record ProductCopy(Boolean found, String id, String name, Category category, int rating, LocalDate createdAt, LocalDate lastModifiedAt) {}
