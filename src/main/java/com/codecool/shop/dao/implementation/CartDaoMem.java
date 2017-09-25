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
    public void add(Product product) {
        String searchingName = product.getName();
        boolean isNotIn = true;

        for (LineItem lineItem: lineItems){
            if (lineItem.getProductName().equals(searchingName)){
                int newQuantity = lineItem.getQuantity()+1;
                lineItem.setQuantity(newQuantity);
                isNotIn = false;
            }
        }
        if (isNotIn){
            LineItem newLineItem = new LineItem(product.getId(),product.getName(), product.getDefaultPrice());
            lineItems.add(newLineItem);
        }

    }

    public static void lineItemTest(){
        for (LineItem lineItem : lineItems){
            System.out.print("id: " + lineItem.getId());
            System.out.print(" name: " + lineItem.getProductName());
            System.out.println(" quantity: " + lineItem.getQuantity());
        }
    }

    @Override
    public void remove(int id) {

    }
}