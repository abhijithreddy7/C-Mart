package com.abhi.cmart.dao;

import com.abhi.cmart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    public List<Order> findByCustomerIdAndIsPlaced(int customerId, boolean isPlaced);

}
