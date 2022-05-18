package com.abhi.cmart.service.interfaces;


import com.abhi.cmart.entity.Order;

import java.util.List;

public interface OrderService {

    public List<Order> findAll();

    public Order findOrderById(int id);

    public List<Order> getCustomerOrders(Integer customerId);

    public List<Order> getCustomerCart(Integer customerId);

    public void save(Order order);

    public void createOrder(int customerId);

    public Order findCartOrder(int customerId);
}
