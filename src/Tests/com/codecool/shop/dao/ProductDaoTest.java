package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDaoTest {

    ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = ProductDaoMem.getInstance();
    }

    @Test
    void constructor_whenSetup_ShouldNotBeNull() {
        assertTrue(productDao != null);
    }

    @Test
    void add_whenAddNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productDao.add(null);
        });
    }

   

}