package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDaoTest {

    private Enum dataHandler;
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private ProductCategory productCategory1;
    private ProductCategory productCategory2;
    private Supplier supplier1;
    private Supplier supplier2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productCategory1 = new ProductCategory("ProductCategory1", "Department", "Description");
        supplier1 = new Supplier("TestSupplier1", "Description");
        product1 = new Product("Product1", 1, "USD", "Description", productCategory1, supplier1, "");

        productCategory2 = new ProductCategory("ProductCategory2", "Department", "Description");
        supplier2 = new Supplier("TestSupplier2", "Description");
        product2 = new Product("Product2", 1, "USD", "Description", productCategory2, supplier2, "");

        dataHandler = Switch.getInstance().getDataHandling();
        if (dataHandler == DataHandler.MEMORY) {
            productDao = ProductDaoMem.getInstance();
            productCategoryDao = ProductCategoryDaoMem.getInstance();
            supplierDao = SupplierDaoMem.getInstance();
            productDao.getAll().clear();
            productCategoryDao.getAll().clear();
            supplierDao.getAll().clear();
        } else {
            productDao = ProductDaoJdbc.getInstance();
            productCategoryDao = ProductCategoryDAOJdbc.getInstance();
            supplierDao = SupplierDaoJdbc.getInstance();
        }

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);
        supplierDao.add(supplier1);
        supplierDao.add(supplier2);
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        if (dataHandler == DataHandler.DATABASE) {
            ProductDaoJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE products CASCADE;");
            ProductDaoJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE productcategories CASCADE;");
            ProductDaoJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE suppliers CASCADE;");
        }
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(productDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        String expectedExceptionMessage = "Added: null, Expected: Product";
        String exceptionMessage = null;
        try {
            productDao.add(null);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, exceptionMessage);
    }

    @Test
    void add_whenAddProduct_shouldStoreOneMore() {
        int expectedNumberOfProducts = 1;
        productDao.add(product1);
        int numberOfProducts = productDao.getAll().size();
        assertEquals(expectedNumberOfProducts, numberOfProducts);
    }

//    @Test
//    void add_whenAddProduct_shouldStoreThatProduct() {
//        productDao.add(product1);
//        int beforeId = productDao.getAll().get(0).getId();
//        productDao.remove(beforeId);
//        product2.setId(beforeId + 1);
//        productDao.add(product2);
//        Product product = productDao.getAll().get(0);
//        assertEquals(convertObjectToList(product2), convertObjectToList(product));
//    }
//
//    @Test
//    void find_whenSearchForExistingId_shouldFindRelatedProduct() {
//        productDao.add(product1);
//        int testId = productDao.getBy(supplier1).get(0).getId();
//        Product product = productDao.find(testId);
//        assertEquals(product1, product);
//    }

    @Test
    void find_whenProductIdDoesNotExist_shouldReturnNull() {
        int nonExistentId = 1;
        Product product = productDao.find(nonExistentId);
        assertEquals(null, product);
    }

//    @Test
//    void remove_whenRemoveProduct_shouldStoreOneLess() {
//        int expectedNumberOfProducts = 0;
//        productDao.add(product1);
//        int testId = productDao.getBy(supplier1).get(0).getId();
//        productDao.remove(testId);
//        int numberOfProducts = productDao.getAll().size();
//        assertEquals(expectedNumberOfProducts, numberOfProducts);
//    }
//
//    @Test
//    void remove_whenRemoveProduct_shouldRemoveRelatedProduct() {
//        List<Product> expectedAllProducts = new ArrayList<>();
//        expectedAllProducts.add(product2);
//
//        productDao.add(product1);
//        productDao.add(product2);
//        int product1Id = productDao.getBy(supplier1).get(0).getId();
//        productDao.remove(product1Id);
//        List<Product> allProducts = productDao.getAll();
//
//        assertEquals(expectedAllProducts, allProducts);
//    }

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

//    @Test
//    void getAll_shouldGiveBackAllProductsInList() {
//        List<Product> expectedAllProducts = new ArrayList<>();
//        expectedAllProducts.add(product1);
//        expectedAllProducts.add(product2);
//
//        productDao.add(product1);
//        productDao.add(product2);
//        List<Product> allProducts = productDao.getAll();
//
//        assertEquals(expectedAllProducts, allProducts);
//    }

    @Test
    void getBy_whenSupplierHasNoProduct_shouldReturnEmptyList() {
        Supplier supplierWithoutProduct = supplier1;
        List<Product> expectedList = new ArrayList<>();
        List<Product> productsList = productDao.getBy(supplierWithoutProduct);
        assertEquals(expectedList, productsList);
    }

    @Test
    void getBy_whenProductCategoryHasNoProduct_shouldReturnEmptyList() {
        ProductCategory productCategoryWithoutProduct = productCategory1;
        List<Product> expectedList = new ArrayList<>();
        List<Product> productsList = productDao.getBy(productCategoryWithoutProduct);
        assertEquals(expectedList, productsList);
    }

//    @Test
//    void getBy_whenSupplierHasProduct_shouldReturnThatProduct() {
//        List<Product> expectedList = new ArrayList<>();
//        expectedList.add(product1);
//
//        productDao.add(product1);
//        List<Product> productsList = productDao.getBy(supplier1);
//
//        assertEquals(expectedList, productsList);
//    }
//
//    @Test
//    void getBy_whenProductCategoryHasProduct_shouldReturnThatProduct() {
//        ProductCategory testProductCategory = productCategory1;
//        List<Product> expectedList = new ArrayList<>();
//        expectedList.add(product1);
//
//        productDao.add(product1);
//        List<Product> productsList = productDao.getBy(testProductCategory);
//
//        assertEquals(expectedList, productsList);
//    }

    private List convertObjectToList(Product product) {
        List<Object> productList = new ArrayList<>();
        productList.add(Integer.toString(product.getId()));
        productList.add(product.getName());
        productList.add(Float.toString(product.getDefaultPrice()));
        productList.add(product.getDefaultCurrency());
        productList.add(product.getProductCategory());
        productList.add(product.getSupplier());
        return productList;
    }
}