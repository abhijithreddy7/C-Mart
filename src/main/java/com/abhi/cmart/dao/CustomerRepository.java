package com.abhi.cmart.dao;

import com.abhi.cmart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);

}
