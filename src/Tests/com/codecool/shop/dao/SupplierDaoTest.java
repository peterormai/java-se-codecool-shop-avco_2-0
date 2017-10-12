package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    private Enum dataHandler;
    private SupplierDao supplierDao;
    private Supplier supplier1;
    private Supplier supplier2;

    @BeforeEach
    void setUp() {
        supplier1 = new Supplier("TestSupplier1", "Description1");
        supplier2 = new Supplier("TestSupplier2", "Description2");

        dataHandler = Switch.getInstance().getDataHandling();
        if (dataHandler == DataHandler.MEMORY) {
            supplierDao = SupplierDaoMem.getInstance();
            supplierDao.getAll().clear();
        } else {
            supplierDao = SupplierDaoJdbc.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        if (dataHandler == DataHandler.DATABASE) {
            SupplierDaoJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE suppliers CASCADE;");
        }
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(supplierDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        String expectedExceptionMessage = "Added: null, Expected: Supplier";
        String exceptionMessage = null;
        try {
            supplierDao.add(null);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, exceptionMessage);
    }

    @Test
    void add_whenAddSupplier_shouldStoreOneMore() {
        int expectedNumberOfSuppliers = 1;
        supplierDao.add(supplier1);

        int numberOfSuppliers = supplierDao.getAll().size();
        assertEquals(expectedNumberOfSuppliers, numberOfSuppliers);
    }

    @Test
    void add_whenAddSupplier_shouldStoreThatSupplier() {
        supplierDao.add(supplier1);
        int beforeId = supplierDao.getAll().get(0).getId();
        supplierDao.remove(beforeId);
        supplier2.setId(beforeId + 1);

        supplierDao.add(supplier2);
        Supplier supplier = supplierDao.getAll().get(0);
        assertEquals(convertObjectToList(supplier2), convertObjectToList(supplier));
    }

    @Test
    void find_whenSearchForExistingId_shouldFindRelatedSupplier() {
        supplierDao.add(supplier1);
        int beforeId = supplierDao.getAll().get(0).getId();
        supplierDao.remove(beforeId);

        supplierDao.add(supplier2);
        int productCategory2Id = beforeId + 1;
        supplier2.setId(productCategory2Id);
        Supplier productCategory = supplierDao.find(productCategory2Id);
        assertEquals(convertObjectToList(supplier2), convertObjectToList(productCategory));
    }

    @Test
    void find_whenSupplierIdDoesNotExist_shouldReturnNull() {
        int nonExistentId = 1;
        Supplier supplier = supplierDao.find(nonExistentId);
        assertEquals(null, supplier);
    }

    @Test
    void remove_whenRemoveSupplier_shouldStoreOneLess() {
        supplierDao.add(supplier1);
        int testId = supplierDao.getAll().get(0).getId();
        supplierDao.remove(testId);

        int expectedNumberOfSuppliers = 0;
        int numberOfSuppliers = supplierDao.getAll().size();

        assertEquals(expectedNumberOfSuppliers, numberOfSuppliers);
    }

    @Test
    void remove_whenRemoveSupplier_shouldRemoveRelatedSupplier() {
        List<Supplier> expectedAllProductCategories = new ArrayList<>();

        supplierDao.add(supplier1);
        int beforeId = supplierDao.getAll().get(0).getId();
        supplierDao.add(supplier2);

        supplierDao.remove(beforeId);

        int productCategory2Id = beforeId + 1;
        supplier2.setId(productCategory2Id);
        expectedAllProductCategories.add(supplier2);

        List<Supplier> allProductCategories = supplierDao.getAll();

        assertEquals(convertObjectToList(expectedAllProductCategories.get(0)), convertObjectToList(allProductCategories.get(0)));
    }

    @Test
    void remove_whenSupplierIdDoesNotExist_shouldNotThrowException() {
        int nonexistentId = 0;
        Exception exception = null;
        try {
            supplierDao.remove(nonexistentId);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(null, exception);
    }

    @Test
    void getAll_whenNoSuppliers_shouldGiveBackEmptyList() {
        List<Supplier> expectedList = new ArrayList<>();
        List<Supplier> suppliersList = supplierDao.getAll();
        assertEquals(expectedList, suppliersList);
    }

    @Test
    void getAll_shouldGiveBackAllSuppliersInList() {
        List<List> expectedAllSuppliers = new ArrayList<>();
        supplierDao.add(supplier1);
        supplierDao.add(supplier2);

        int productCategory1Id = supplierDao.getAll().get(0).getId();
        int productCategory2Id = supplierDao.getAll().get(1).getId();

        supplier1.setId(productCategory1Id);
        supplier2.setId(productCategory2Id);

        expectedAllSuppliers.add(convertObjectToList(supplier1));
        expectedAllSuppliers.add(convertObjectToList(supplier2));

        List<List> allProductCategories = new ArrayList<>();
        for (Supplier prodCat: supplierDao.getAll()) {
            allProductCategories.add(convertObjectToList(prodCat));
        }
        assertEquals(expectedAllSuppliers, allProductCategories);
    }

    private List convertObjectToList(Supplier supplier) {
        List<String> supplierList = new ArrayList<>();
        supplierList.add(Integer.toString(supplier.getId()));
        supplierList.add(supplier.getName());
        supplierList.add(supplier.getDescription());
        return supplierList;
    }
}