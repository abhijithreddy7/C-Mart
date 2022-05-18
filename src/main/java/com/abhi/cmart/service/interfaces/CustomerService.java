package com.abhi.cmart.service.interfaces;


import com.abhi.cmart.entity.Customer;
import com.abhi.cmart.utilities.CustomerSignUp;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();

    public void save(CustomerSignUp customer);

    public void save(Customer customer);

    public void deleteCustomer(int id);

    public Customer findCustomerById(int id);

    public boolean isNewEmail(String email);

    public Customer findCustomerByEmail(String email);

    public String getCurrentUserEmail();

    public int getCustomerIdByEmail(String email);

    public Customer encodePassword(Customer customer);

}
