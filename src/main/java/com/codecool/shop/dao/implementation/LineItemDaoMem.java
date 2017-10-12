package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.OrderStatus;
import com.codecool.shop.model.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {
    private static LineItemDaoMem lineItemDaoMem = null;
    private static List<LineItem> lineItems = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;

    private LineItemDaoMem() {
    }

    public static LineItemDaoMem getInstance() {
        if ( lineItemDaoMem == null) {
            lineItemDaoMem = new LineItemDaoMem();
        }
        return lineItemDaoMem;
    }

    @Override
    public void add(Product product) {
        boolean isNotIn = true;

        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product)) {
                int newQuantity = lineItem.getQuantity() + 1;
                lineItem.setQuantity(newQuantity);
                isNotIn = false;
            }
        }
        if (isNotIn) {
            LineItem newLineItem = new LineItem(1, product, 1, 1);
            lineItems.add(newLineItem);
        }
    }


    @Override
    public void remove(Product product) {

    }

    @Override
    public void removeLineItem(int id) {
        for (LineItem item : lineItems) {
            if (item.getId() == id) {
                lineItems.remove(item);
                break;
            }
        }
    }

    @Override
    public void removeAll() {
        lineItems = new ArrayList<>();
    }

    @Override
    public LineItem get(int id) {
        for (LineItem lineItem: lineItems){
            if (lineItem.getId() == id){
                return lineItem;
            }
        }
        return null;
    }

    @Override
    public List<LineItem> getAll() {
        return lineItems;
    }

    @Override
    public int getNumberOfItem() {
        int numberOfItems = 0;
        for (LineItem lineItem : lineItems) {
            numberOfItems += lineItem.getQuantity();
        }
        return numberOfItems;

    }

    @Override
    public int getTotalPrice() {
        float totalPrice = 0;
        for (LineItem lineItem : lineItems) {
            totalPrice += lineItem.getLineItemsPrice();
        }
        return (int) totalPrice;
    }

    @Override
    public void changeItemQuantity(int id, int quantity) {
        int num = quantity;
        int validId = id;
        if (num == 0) {
            removeLineItem(validId);
        } else {
            for (LineItem item : lineItems) {
                if (item.getId() == validId) {
                    item.setQuantity(num);
                }
            }
        }
    }

    @Override
    public void JSONFileWrite() {
        JSONObject orders = new JSONObject();
        JSONObject order = new JSONObject();
        JSONArray lineItemsOfOrder = new JSONArray();
        for (LineItem lineItem : lineItems) {
            JSONObject lineItemObj = new JSONObject();
            JSONObject productObj = new JSONObject();
            lineItemObj.put("id", lineItem.getId());
            lineItemObj.put("quantity", lineItem.getQuantity());

            productObj.put("productName", lineItem.getProduct().getName());
            productObj.put("defaultPrice", lineItem.getProduct().getDefaultPrice());
            productObj.put("defaultCurrency", lineItem.getProduct().getDefaultCurrency());
            productObj.put("productCategory", lineItem.getProduct().getProductCategory().getName());
            productObj.put("supplier", lineItem.getProduct().getSupplier().getName());

            lineItemObj.put("product", productObj);
            lineItemsOfOrder.add(lineItemObj);
        }

        orders.put("firstOrder", order);
        order.put("lineItems", lineItemsOfOrder);

        try (FileWriter file = new FileWriter("src/main/resources/log/orders.txt")) {
            file.write(orders.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
