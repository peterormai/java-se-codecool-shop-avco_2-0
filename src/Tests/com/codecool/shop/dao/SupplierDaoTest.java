package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    private SupplierDao supplierDao = SupplierDaoMem.getInstance();

    @BeforeEach
    void setUp() {
        supplierDao.getAll().clear();
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

}