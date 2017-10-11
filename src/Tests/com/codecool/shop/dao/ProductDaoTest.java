package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDaoTest {

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = ProductDaoMem.getInstance();
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(productDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productDao.add(null);
        });
    }

    @Test
    void add_whenAddProduct_shouldStoreOneMore() {
        int expectedNumberOfProducts = productDao.getAll().size() + 1;
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier exampleSupplier = new Supplier("Supplier", "Description");
        Product exampleProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, exampleSupplier);

        productDao.add(exampleProduct);

        int numberOfProducts = productDao.getAll().size();
        assertEquals(expectedNumberOfProducts, numberOfProducts);
    }

    @Test
    void add_whenAddNewProduct_shouldStoreThatProduct() {
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier testSupplier = new Supplier("TestSupplier", "Description");
        Product expectedProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, testSupplier);

        productDao.add(expectedProduct);

        Product product = productDao.getBy(testSupplier).get(0);
        assertEquals(expectedProduct, product);
    }

    @Test
    void find_whenSearchForExistingId_shouldFindRelatedProduct() {
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier testSupplier = new Supplier("TestSupplier", "Description");
        Product expectedProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, testSupplier);

        productDao.add(expectedProduct);

        int testId = productDao.getBy(testSupplier).get(0).getId();
        Product product = productDao.find(testId);
        assertEquals(expectedProduct, product);
    }




}