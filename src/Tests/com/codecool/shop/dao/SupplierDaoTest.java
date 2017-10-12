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

    @BeforeEach
    void setUp() {
        dataHandler = Switch.getInstance().getDataHandling();
        if(dataHandler == DataHandler.MEMORY) {
            supplierDao = SupplierDaoMem.getInstance();
            supplierDao.getAll().clear();
        } else {
            supplierDao = SupplierDaoJdbc.getInstance();
        }
    }

    @AfterEach
    public void tearDown() {
        // delete test data
        if(dataHandler == DataHandler.DATABASE) {
            SupplierDaoJdbc.getInstance().executeQueryWithNoReturnValue("TRUNCATE TABLE suppliers CASCADE;");
        }
    }

    @Test
    void constructor_whenGetInstance_shouldNotBeNull() {
        assertTrue(supplierDao != null);
    }

    @Test
    void add_whenAddNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            supplierDao.add(null);
        });
    }

    @Test
    void add_whenAddSupplier_shouldStoreOneMore() {
        int expectedNumberOfSuppliers = 1;
        Supplier exampleSupplier = new Supplier("Supplier", "Description");
        supplierDao.add(exampleSupplier);

        int numberOfSuppliers = supplierDao.getAll().size();
        assertEquals(expectedNumberOfSuppliers, numberOfSuppliers);
    }

    @Test
    void add_whenAddSupplier_shouldStoreThatSupplier() {
        Supplier expectedSupplier = new Supplier("TestSupplier", "Description");
        supplierDao.add(expectedSupplier);

        Supplier supplier = supplierDao.getAll().get(0);
        assertEquals(expectedSupplier, supplier);
    }

    @Test
    void find_whenSearchForExistingId_shouldFindRelatedSupplier() {
        Supplier expectedSupplier = new Supplier("TestSupplier", "Description");
        supplierDao.add(expectedSupplier);

        int expectedSupplierId = supplierDao.getAll().get(0).getId();
        Supplier supplier = supplierDao.find(expectedSupplierId);
        assertEquals(expectedSupplier, supplier);
    }

    @Test
    void find_whenSupplierIdDoesNotExist_shouldReturnNull() {
        int nonExistentId = 1;
        Supplier supplier = supplierDao.find(nonExistentId);
        assertEquals(null, supplier);
    }

    @Test
    void remove_whenRemoveSupplier_shouldStoreOneLess() {
        int expectedNumberOfSuppliers = 0;
        Supplier testSupplier = new Supplier("TestSupplier", "Description");

        supplierDao.add(testSupplier);
        int testSupplierId = supplierDao.getAll().get(0).getId();
        supplierDao.remove(testSupplierId);

        int numberOfSuppliers = supplierDao.getAll().size();
        assertEquals(expectedNumberOfSuppliers, numberOfSuppliers);
    }

    @Test
    void remove_whenRemoveSupplier_shouldRemoveRelatedSupplier() {
        Supplier supplier1 = new Supplier("TestSupplier", "Description");
        Supplier supplier2 = new Supplier("TestSupplier", "Description");

        List<Supplier> expectedAllSuppliers = new ArrayList<>();
        expectedAllSuppliers.add(supplier2);

        supplierDao.add(supplier1);
        supplierDao.add(supplier2);

        int supplier1Id = supplierDao.getAll().get(0).getId();
        supplierDao.remove(supplier1Id);

        List<Supplier> allSuppliers = supplierDao.getAll();

        assertEquals(expectedAllSuppliers, allSuppliers);
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
        Supplier supplier1 = new Supplier("TestSupplier", "Description");
        Supplier supplier2 = new Supplier("TestSupplier", "Description");

        List<Supplier> expectedAllSuppliers = new ArrayList<>();
        expectedAllSuppliers.add(supplier1);
        expectedAllSuppliers.add(supplier2);

        supplierDao.add(supplier1);
        supplierDao.add(supplier2);

        List<Supplier> allSuppliers = supplierDao.getAll();

        assertEquals(expectedAllSuppliers, allSuppliers);
    }

}