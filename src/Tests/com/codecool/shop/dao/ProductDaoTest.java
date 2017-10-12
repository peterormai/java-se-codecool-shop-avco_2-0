package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDaoTest {

    private ProductDao productDao = ProductDaoMem.getInstance();

    @BeforeEach
    void setUp() {
        productDao.getAll().clear();
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
        int expectedNumberOfProducts = 1;
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier exampleSupplier = new Supplier("Supplier", "Description");
        Product exampleProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, exampleSupplier);

        productDao.add(exampleProduct);

        int numberOfProducts = productDao.getAll().size();
        assertEquals(expectedNumberOfProducts, numberOfProducts);
    }

    @Test
    void add_whenAddProduct_shouldStoreThatProduct() {
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

    @Test
    void find_whenIdDoesNotExist_shouldReturnNull() {
        int nonExistentId = 1;
        Product product = productDao.find(nonExistentId);
        assertEquals(null, product);
    }

    @Test
    void remove_whenRemoveProduct_shouldStoreOneLess() {
        int expectedNumberOfProducts = 0;

        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier testSupplier = new Supplier("TestSupplier", "Description");
        Product expectedProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, testSupplier);

        productDao.add(expectedProduct);
        int testId = productDao.getBy(testSupplier).get(0).getId();
        productDao.remove(testId);

        int numberOfProducts = productDao.getAll().size();
        assertEquals(expectedNumberOfProducts, numberOfProducts);
    }

    @Test
    void remove_whenRemoveProduct_shouldRemoveRelatedProduct() {
        ProductCategory productCategory1 = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier supplier1 = new Supplier("TestSupplier", "Description");
        Product product1 = new Product("Product", 0, "USD", "Description", productCategory1, supplier1);

        ProductCategory productCategory2 = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier supplier2 = new Supplier("TestSupplier", "Description");
        Product product2 = new Product("Product", 0, "USD", "Description", productCategory2, supplier2);

        List<Product> expectedAllProducts = new ArrayList<>();
        expectedAllProducts.add(product2);

        productDao.add(product1);
        productDao.add(product2);

        int product1Id = productDao.getBy(supplier1).get(0).getId();
        productDao.remove(product1Id);

        List<Product> allProducts = productDao.getAll();

        assertEquals(expectedAllProducts, allProducts);
    }

    @Test
    void remove_whenIdDoesNotExist_shouldNotThrowException() {
        int nonexistentId = 0;
        Exception exception = null;
        try {
            productDao.remove(nonexistentId);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(null, exception);
    }

    @Test
    void getAll_whenNoProducts_shouldGiveBackEmptyList() {
        List<Product> expectedList = new ArrayList<>();
        List<Product> productsList = productDao.getAll();
        assertEquals(expectedList, productsList);
    }

    @Test
    void getAll_shouldGiveBackAllProductsInList() {
        ProductCategory productCategory1 = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier supplier1 = new Supplier("TestSupplier", "Description");
        Product product1 = new Product("Product", 0, "USD", "Description", productCategory1, supplier1);

        ProductCategory productCategory2 = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier supplier2 = new Supplier("TestSupplier", "Description");
        Product product2 = new Product("Product", 0, "USD", "Description", productCategory2, supplier2);

        List<Product> expectedAllProducts = new ArrayList<>();
        expectedAllProducts.add(product1);
        expectedAllProducts.add(product2);

        productDao.add(product1);
        productDao.add(product2);

        List<Product> allProducts = productDao.getAll();

        assertEquals(expectedAllProducts, allProducts);
    }

    @Test
    void getBy_whenSupplierHasNoProduct_shouldReturnEmptyList() {
        Supplier supplierWithoutProduct = new Supplier("TestSupplier", "Description");
        List<Product> expectedList = new ArrayList<>();
        List<Product> productsList = productDao.getBy(supplierWithoutProduct);
        assertEquals(expectedList, productsList);
    }

    @Test
    void getBy_whenProductCategoryHasNoProduct_shouldReturnEmptyList() {
        ProductCategory productCategoryWithoutProduct = new ProductCategory("ProductCategory", "Department", "Description");
        List<Product> expectedList = new ArrayList<>();
        List<Product> productsList = productDao.getBy(productCategoryWithoutProduct);
        assertEquals(expectedList, productsList);
    }

    @Test
    void getBy_whenSupplierHasProduct_shouldReturnThatProduct() {
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");
        Supplier testSupplier = new Supplier("TestSupplier", "Description");
        Product expectedProduct = new Product("Product", 0, "USD", "Description", exampleProductCategory, testSupplier);
        productDao.add(expectedProduct);

        List<Product> expectedList = new ArrayList<>();
        expectedList.add(expectedProduct);

        List<Product> productsList = productDao.getBy(testSupplier);

        assertEquals(expectedList, productsList);
    }

    @Test
    void getBy_whenProductCategoryHasProduct_shouldReturnThatProduct() {
        Supplier exampleSupplier = new Supplier("Supplier", "Description");
        ProductCategory testProductCategory = new ProductCategory("TestProductCategory", "Department", "Description");
        Product expectedProduct = new Product("Product", 0, "USD", "Description", testProductCategory, exampleSupplier);
        productDao.add(expectedProduct);

        List<Product> expectedList = new ArrayList<>();
        expectedList.add(expectedProduct);

        List<Product> productsList = productDao.getBy(testProductCategory);

        assertEquals(expectedList, productsList);
    }

}