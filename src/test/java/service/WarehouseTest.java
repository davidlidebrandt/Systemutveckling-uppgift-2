package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import entities.Category;
import entities.Product;
import entities.ProductCopy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    Warehouse warehouse;
    LocalDate createdAt = LocalDate.now().minusDays(20);
    LocalDate createdLater = LocalDate.now();

    @Test
    void testGetEmptyProductListBeforeAddingProducts() {
        warehouse = new Warehouse();
        assertEquals(new ArrayList<ProductCopy>(), warehouse.getAllProducts());
    }


    @Test
    void testAddProduct() {
        warehouse = new Warehouse();

        assertNotEquals("", warehouse.addProduct("Test", Category.CLOTHES));
        assertEquals("", warehouse.addProduct("", Category.SHOES));
        assertTrue(warehouse.getAllProducts().stream().anyMatch(p -> "Test".equals(p.name())));
        assertEquals(1, warehouse.getAllProducts().size());
    }

    @Test
    void testGetModifiedProducts() {
        warehouse = new Warehouse(getPopulatedList());
        assertEquals(4, warehouse.getModifiedProducts().size());
    }

    @Test
    void testGetProduct() {
        warehouse = new Warehouse(getPopulatedList());

        assertEquals(true, warehouse.getProduct("1323").found());
        assertEquals(false, warehouse.getProduct(" ").found());
    }

    @Test
    void testGetProductsAddedAfterGivenDate() {
        warehouse = new Warehouse(getPopulatedList());
        List<ProductCopy> products = new ArrayList<>();
        products.add(new ProductCopy(true, "1", "Big shoes", Category.SHOES, 3, createdLater, createdAt));
        products.add(
                 new ProductCopy(true, "4", "Big clothes", Category.CLOTHES, 3, createdLater, createdAt));

        assertEquals(products.size(), warehouse.getProductsAddedAfterGivenDate(createdAt).size());
        assertEquals(products.get(0).name(), warehouse.getProductsAddedAfterGivenDate(createdAt).get(0).name());
        assertEquals(products.get(1).name(), warehouse.getProductsAddedAfterGivenDate(createdAt).get(1).name());

    }

    @Test
    void testGetProductsByCategory() {
        warehouse = new Warehouse(getPopulatedList());
        List<ProductCopy> products = new ArrayList<>();
        products.add(new ProductCopy(true, "6", "Big clothes", Category.CLOTHES, 3, createdAt,
                LocalDate.now().plusDays(4)));
        products.add(new ProductCopy(true, "5", "Medium clothes", Category.CLOTHES, 3, createdAt, createdAt));
        products.add(
                new ProductCopy(true, "4", "Small clothes", Category.CLOTHES, 3, createdLater, createdAt));

        assertEquals(products.size(), warehouse.getProductsByCategory(Category.CLOTHES).size());
        assertEquals(products.get(0).name(), warehouse.getProductsByCategory(Category.CLOTHES).get(0).name());
        assertEquals(products.get(1).name(), warehouse.getProductsByCategory(Category.CLOTHES).get(1).name());
        assertEquals(products.get(2).name(), warehouse.getProductsByCategory(Category.CLOTHES).get(2).name());
    }

    @Test
    void testUpdateCategory() {
        warehouse = new Warehouse(getPopulatedList());
        List<ProductCopy> productsCopy = new ArrayList<>();
        for (ProductCopy p : warehouse.getAllProducts()) {
            if(Objects.equals(p.id(), "1323")) {
                productsCopy.add(new ProductCopy(true, p.id(), p.name(), Category.CLOTHES, p.rating(),
                    p.createdAt(), p.lastModifiedAt()));
            }
            else {
                productsCopy.add(new ProductCopy(true, p.id(), p.name(), p.category(), p.rating(),
                    p.createdAt(), p.lastModifiedAt()));
            }
        }

        assertNotEquals(productsCopy, warehouse.getAllProducts());
        assertEquals("Successfully updated", warehouse.updateCategory("1323", Category.CLOTHES));;
        assertEquals(productsCopy, warehouse.getAllProducts());

    }

    @Test
    void testUpdateName() {
        warehouse = new Warehouse(getPopulatedList());
        List<ProductCopy> productsCopy = new ArrayList<>();
        for (ProductCopy p : warehouse.getAllProducts()) {
            if(Objects.equals(p.id(), "1323")) {
                productsCopy.add(new ProductCopy(true, p.id(), "A new long name", p.category(), p.rating(),
                    p.createdAt(), p.lastModifiedAt()));
            }
            else {
                productsCopy.add(new ProductCopy(true, p.id(), p.name(), p.category(), p.rating(),
                    p.createdAt(), p.lastModifiedAt()));
            }
        }
        assertNotEquals(productsCopy, warehouse.getAllProducts());
        assertEquals("Successfully updated", warehouse.updateName("1323", "A new long name"));;
        assertEquals(productsCopy, warehouse.getAllProducts());
    }

    @Test
    void testUpdateRating() {
        warehouse = new Warehouse(getPopulatedList());
        List<ProductCopy> productsCopy = getProductCopies();
        assertNotEquals(productsCopy, warehouse.getAllProducts());
        assertEquals("Successfully updated", warehouse.updateRating("1323", 1));
        assertEquals("Error updating", warehouse.updateRating("1323", 11));
        assertEquals("Error updating", warehouse.updateRating("1323", 0));
        assertEquals(productsCopy, warehouse.getAllProducts());
    }

    private List<ProductCopy> getProductCopies() {
        List<ProductCopy> productsCopy = new ArrayList<>();
        for (ProductCopy p : warehouse.getAllProducts()) {
            if(p.id() == "1323") {
                productsCopy.add(new ProductCopy(true, p.id(), p.name(), p.category(), 1,
                    p.createdAt(), p.lastModifiedAt()));
            }
            else {
                productsCopy.add(new ProductCopy(true, p.id(), p.name(), p.category(), p.rating(),
                    p.createdAt(), p.lastModifiedAt()));
            }
        }
        return productsCopy;
    }

    CopyOnWriteArrayList<Product> getPopulatedList() {
        CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<Product>();
        products.add(new Product("3232", "Small shoes", Category.SHOES, 3, createdAt));
        products.add(new Product("437437", "Medium shoes", Category.SHOES, 7, createdAt));
        products.add(new Product("1323", "Big shoes", Category.SHOES, 4, createdLater));
        products.add(new Product("45454", "Small clothes", Category.CLOTHES, 5, createdAt));
        products.add(new Product("8988", "Medium clothes", Category.CLOTHES, 7, createdAt));
        products.add(new Product("43344", "Big clothes", Category.CLOTHES, 4, createdLater));

        return products;
    }
}
