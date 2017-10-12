package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDAOJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    private Enum dataHandler;
    private ProductCategoryDao productCategoryDao;
    private ProductCategory productCategory1;
    private ProductCategory productCategory2;

    @BeforeEach
    void setUp() {
        productCategory1 = new ProductCategory("ProductCategory", "Department", "Description");
        productCategory2 = new ProductCategory("ProductCategory", "Department", "Description");

        dataHandler = Switch.getInstance().getDataHandling();
        if (dataHandler == DataHandler.MEMORY) {
            productCategoryDao = ProductCategoryDaoMem.getInstance();
            productCategoryDao.getAll().clear();
        } else {
            productCategoryDao = ProductCategoryDAOJdbc.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        if (dataHandler == DataHandler.DATABASE) {
            ProductCategoryDAOJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE productcs CASCADE;");
        }
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
    void add_whenAddProductCategory_shouldStoreOneMore() {
        int expectedNumberOfProductCategories = 1;
        productCategoryDao.add(productCategory1);
        int numberOfProductCategories = productCategoryDao.getAll().size();
        assertEquals(expectedNumberOfProductCategories, numberOfProductCategories);
    }

    //
    @Test
    void add_whenAddNewProductCategory_shouldStoreThatProductCategory() {
        productCategoryDao.add(productCategory1);
        ProductCategory productCategory = productCategoryDao.getAll().get(0);
        assertEquals(productCategory1, productCategory);
    }

    //
    @Test
    void find_whenSearchForExistingId_shouldFindRelatedProductCategory() {
        productCategoryDao.add(productCategory1);
        int testId = productCategoryDao.getAll().get(0).getId();
        ProductCategory productCategory = productCategoryDao.find(testId);
        assertEquals(productCategory1, productCategory);
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
    void remove_whenRemoveProductCategory_shouldStoreOneLess() {
        int expectedNumberOfProductCategories = 0;

        productCategoryDao.add(productCategory1);
        int testId = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.remove(testId);
        int numberOfProductCategories = productCategoryDao.getAll().size();

        assertEquals(expectedNumberOfProductCategories, numberOfProductCategories);
    }

    //
    @Test
    void remove_whenRemoveProductCategories_shouldRemoveRelatedProductCategory() {
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
    void getAll_whenNoProductCategories_shouldGiveBackEmptyList() {
        List<ProductCategory> expectedCategoryList = new ArrayList<>();
        List<ProductCategory> productCategoryList = productCategoryDao.getAll();
        assertEquals(expectedCategoryList, productCategoryList);
    }

    //
    @Test
    void getAll_shouldGiveBackAllProductCategoriesInList() {
        List<ProductCategory> expectedAllProductCategories = new ArrayList<>();
        expectedAllProductCategories.add(productCategory1);
        expectedAllProductCategories.add(productCategory2);

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);

        List<ProductCategory> allProductCategories = productCategoryDao.getAll();

        assertEquals(expectedAllProductCategories, allProductCategories);
    }

}