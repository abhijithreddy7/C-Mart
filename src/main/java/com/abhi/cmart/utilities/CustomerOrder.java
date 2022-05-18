package com.abhi.cmart.utilities;


import com.abhi.cmart.entity.Customer;
import com.abhi.cmart.entity.Item;
import com.abhi.cmart.entity.Order;

import java.util.List;

public class CustomerOrder {
    private int id;
    private boolean isPlaced;
    private Customer customer;
    private List<Item> items;
    private int cost;

    public CustomerOrder(Order order) {
        if(order != null) {
            this.id = order.getId();
            this.isPlaced = order.isPlaced();
            this.customer = order.getCustomer();
            this.items = order.getItems();
            this.cost = getTotalCost();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    private int getTotalCost() {
        int sum = 0;
        for(Item item : items) {
            sum += item.getCost();
        }
        return sum;
    }
}