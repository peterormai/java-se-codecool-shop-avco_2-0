package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();

    @BeforeEach
    void setUp() {
        productCategoryDao.getAll().clear();
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(productCategoryDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productCategoryDao.add(null);
        });
    }

    @Test
    void add_whenAddProduct_shouldStoreOneMore() {
        int expectedNumberOfProductCategories = 1;
        ProductCategory exampleProductCategory = new ProductCategory("ProductCategory", "Department", "Description");

        productCategoryDao.add(exampleProductCategory);

        int numberOfProductCategories = productCategoryDao.getAll().size();
        assertEquals(expectedNumberOfProductCategories, numberOfProductCategories);
    }
//
    @Test
    void add_whenAddNewProduct_shouldStoreThatProduct() {
        ProductCategory expectedProductCategory = new ProductCategory("ProductCategory", "Department", "Description");

        productCategoryDao.add(expectedProductCategory);

        ProductCategory productCategory = productCategoryDao.getAll().get(0);
        assertEquals(expectedProductCategory, productCategory);
    }
//
    @Test
    void find_whenSearchForExistingId_shouldFindRelatedProduct() {
        ProductCategory expectedProductCategory = new ProductCategory("ProductCategory", "Department", "Description");

        productCategoryDao.add(expectedProductCategory);

        int testId = productCategoryDao.getAll().get(0).getId();
        ProductCategory productCategory = productCategoryDao.find(testId);
        assertEquals(expectedProductCategory, productCategory);
    }
//
    @Test
    void find_whenIdDoesNotExist_shouldReturnNull() {
        int nonExistentId = 1;
        ProductCategory productCategory = productCategoryDao.find(nonExistentId);
        assertEquals(null, productCategory);
    }
//
    @Test
    void remove_whenRemoveProduct_shouldStoreOneLess() {
        int expectedNumberOfProductCategories = 0;

        ProductCategory expectedProductCategory = new ProductCategory("ProductCategory", "Department", "Description");

        productCategoryDao.add(expectedProductCategory);
        int testId = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.remove(testId);

        int numberOfProductCategories = productCategoryDao.getAll().size();
        assertEquals(expectedNumberOfProductCategories, numberOfProductCategories);
    }
//
    @Test
    void remove_whenRemoveProduct_shouldRemoveRelatedProduct() {
        ProductCategory productCategory1 = new ProductCategory("ProductCategory", "Department", "Description");
        ProductCategory productCategory2 = new ProductCategory("ProductCategory", "Department", "Description");

        List<ProductCategory> expectedAllProductCategories = new ArrayList<>();
        expectedAllProductCategories.add(productCategory2);

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);

        int productCategory1Id = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.remove(productCategory1Id);

        List<ProductCategory> allProductCategories = productCategoryDao.getAll();

        assertEquals(expectedAllProductCategories, allProductCategories);
    }
//
    @Test
    void remove_whenIdDoesNotExist_shouldNotThrowException() {
        int nonexistentId = 0;
        Exception exception = null;
        try {
            productCategoryDao.remove(nonexistentId);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(null, exception);
    }
//
    @Test
    void getAll_whenNoProducts_shouldGiveBackEmptyList() {
        List<ProductCategory> expectedCategoryList = new ArrayList<>();
        List<ProductCategory> productCategoryList = productCategoryDao.getAll();
        assertEquals(expectedCategoryList, productCategoryList);
    }
//
    @Test
    void getAll_shouldGiveBackAllProductsInList() {
        ProductCategory productCategory1 = new ProductCategory("ProductCategory", "Department", "Description");
        ProductCategory productCategory2 = new ProductCategory("ProductCategory", "Department", "Description");

        List<ProductCategory> expectedAllProductCategories = new ArrayList<>();
        expectedAllProductCategories.add(productCategory1);
        expectedAllProductCategories.add(productCategory2);

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);

        List<ProductCategory> allProductCategories = productCategoryDao.getAll();

        assertEquals(expectedAllProductCategories, allProductCategories);
    }

}