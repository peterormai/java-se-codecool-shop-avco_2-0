package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao{
    private static CartDaoMem ourInstance = null;
    private static List<LineItem> lineItems = new ArrayList();

    public static CartDaoMem getInstance() {
        if (ourInstance == null){
            ourInstance = new CartDaoMem();
        }
        return ourInstance;
    }

    private CartDaoMem() {
    }


    @Override
    public void add(Product cart) {

    }

    @Override
    public void remove(int id) {

    }
}