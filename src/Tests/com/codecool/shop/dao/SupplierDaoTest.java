package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}