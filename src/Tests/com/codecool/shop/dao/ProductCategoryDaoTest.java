package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDAOJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.Product;
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
        productCategory1 = new ProductCategory("ProductCategory1", "Department1", "Description1");
        productCategory2 = new ProductCategory("ProductCategory2", "Department2", "Description2");

        dataHandler = Switch.getInstance().getDataHandling();
        if (dataHandler == DataHandler.MEMORY) {
            productCategoryDao = ProductCategoryDaoMem.getInstance();
            productCategoryDao.getAll().clear();
        } else {
            ProductCategoryDAOJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE productcategories CASCADE;");
            productCategoryDao = ProductCategoryDAOJdbc.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        if (dataHandler == DataHandler.DATABASE) {
            ProductCategoryDAOJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE productcategories CASCADE;");
        }
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(productCategoryDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        String expectedExceptionMessage = "Added: null, Expected: ProductCategory";
        String exceptionMessage = null;
        try {
            productCategoryDao.add(null);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, exceptionMessage);
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
        int beforeId = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.remove(beforeId);
        productCategory2.setId(beforeId + 1);
        productCategoryDao.add(productCategory2);
        ProductCategory productCategory = productCategoryDao.getAll().get(0);
        assertEquals(convertObjectToList(productCategory2), convertObjectToList(productCategory));
    }

    //
    @Test
    void find_whenSearchForExistingId_shouldFindRelatedProductCategory() {
        productCategoryDao.add(productCategory1);
        int beforeId = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.remove(beforeId);

        productCategoryDao.add(productCategory2);
        int productCategory2Id = beforeId + 1;
        productCategory2.setId(productCategory2Id);
        ProductCategory productCategory = productCategoryDao.find(productCategory2Id);
        assertEquals(convertObjectToList(productCategory2), convertObjectToList(productCategory));
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

        productCategoryDao.add(productCategory1);
        int beforeId = productCategoryDao.getAll().get(0).getId();
        productCategoryDao.add(productCategory2);

        productCategoryDao.remove(beforeId);

        int productCategory2Id = beforeId + 1;
        productCategory2.setId(productCategory2Id);
        expectedAllProductCategories.add(productCategory2);

        List<ProductCategory> allProductCategories = productCategoryDao.getAll();

        assertEquals(convertObjectToList(expectedAllProductCategories.get(0)), convertObjectToList(allProductCategories.get(0)));
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
        List<List> expectedAllProductCategories = new ArrayList<>();
        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);

        int productCategory1Id = productCategoryDao.getAll().get(0).getId();
        int productCategory2Id = productCategoryDao.getAll().get(1).getId();

        productCategory1.setId(productCategory1Id);
        productCategory2.setId(productCategory2Id);

        expectedAllProductCategories.add(convertObjectToList(productCategory1));
        expectedAllProductCategories.add(convertObjectToList(productCategory2));

        List<List> allProductCategories = new ArrayList<>();
        for (ProductCategory prodCat: productCategoryDao.getAll()) {
            allProductCategories.add(convertObjectToList(prodCat));
        }

        assertEquals(expectedAllProductCategories, allProductCategories);
    }

    private List convertObjectToList(ProductCategory productCategory) {
        List<String> productCategoryList = new ArrayList<>();
        productCategoryList.add(Integer.toString(productCategory.getId()));
        productCategoryList.add(productCategory.getName());
        productCategoryList.add(productCategory.getDepartment());
        productCategoryList.add(productCategory.getDescription());
        return productCategoryList;
    }

}