package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import entities.Category;
import entities.Product;
import entities.ProductCopy;

public class Warehouse {
    private CopyOnWriteArrayList<Product> allProducts;

    public Warehouse(CopyOnWriteArrayList<Product> products) {
        this.allProducts = products;
    }

    public Warehouse() {
        this.allProducts = new CopyOnWriteArrayList<>();
    }

    public String addProduct(String name, Category category) {

        try {
            Product newProduct = new Product(name, category);
            allProducts.add(newProduct);
            return newProduct.getId();
        } catch (Exception e) {
            return "";
        }
    }

    public List<ProductCopy> getAllProducts() {
        // Returns a list of all saved products
        // Could be empty if no products have been added
        List<ProductCopy> productsCopy = new ArrayList<>();
        for (Product p : allProducts) {
            productsCopy.add(new ProductCopy(true, p.getId(), p.getName(), p.getCategory(), p.getRating(),
                    p.getCreatedAt(), p.getLastModifiedAt()));
        }
        return productsCopy;
    }

    public ProductCopy getProduct(String id) {
        // Returns a ProductCopy object with field found set to true if found
        // Returns a ProductCopy object with field found set to false if not found
        for (Product p : allProducts) {
            if (Objects.equals(p.getId(), id)) {
                return new ProductCopy(true, p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedAt(),
                        p.getLastModifiedAt());
            }
        }
        return new ProductCopy(false, "", "", Category.values()[0], 0, LocalDate.now(), LocalDate.now());
    }

    public List<ProductCopy> getProductsByCategory(Category category) {
        return allProducts.stream().filter((p) -> p.getCategory() == category).map((p) -> new ProductCopy(true, p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedAt(),
                p.getLastModifiedAt())).sorted(Comparator.comparing(ProductCopy::name)).toList();
    }

    public List<ProductCopy> getProductsAddedAfterGivenDate(LocalDate startDate) {
        return allProducts.stream().filter((p) -> p.getCreatedAt().isAfter(startDate)).map((p) -> new ProductCopy(true, p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedAt(),
                    p.getLastModifiedAt()))
        .toList();
    }

    public List<ProductCopy> getModifiedProducts() {
        return allProducts.stream().filter((p) -> p.getCreatedAt() != p.getLastModifiedAt() && p.getCreatedAt().isBefore(p.getLastModifiedAt())).map((p) -> { return new ProductCopy(true, p.getId(), p.getName(), p.getCategory(), p.getRating(), p.getCreatedAt(),
                    p.getLastModifiedAt());})
        .toList();
    }

    public String updateName(String id, String newName) {

        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (Objects.equals(p.getId(), id)) {
                Product newProduct = new Product(p.getId(), newName, p.getCategory(), p.getRating(), p.getCreatedAt());
                allProducts.set(i, newProduct);
                return "Successfully updated";
            }
        }
        return "Error updating";

    }

    public String updateRating(String id, int newRating) {
        if (newRating < 1 || newRating > 10) {
            return "Error updating";
        }
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (Objects.equals(p.getId(), id)) {
                Product newProduct = new Product(p.getId(), p.getName(), p.getCategory(), newRating, p.getCreatedAt());
                allProducts.set(i, newProduct);
                return "Successfully updated";
            }
        }
        return "Error updating";
    }

    public String updateCategory(String id, Category newCategory) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (Objects.equals(p.getId(), id)) {
                Product newProduct = new Product(p.getId(), p.getName(), newCategory, p.getRating(), p.getCreatedAt());
                allProducts.set(i, newProduct);
                return "Successfully updated";
            }
        }
        return "Error updating";
    }
}
