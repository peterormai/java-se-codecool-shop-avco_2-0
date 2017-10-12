package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    private SupplierDao supplierDao = SupplierDaoMem.getInstance();

    @BeforeEach
    void setUp() {
        supplierDao.getAll().clear();
    }

}