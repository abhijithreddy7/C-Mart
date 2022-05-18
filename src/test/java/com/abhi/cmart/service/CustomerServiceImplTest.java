package com.abhi.cmart.service;

import com.abhi.cmart.dao.CustomerRepository;
import com.abhi.cmart.entity.Customer;
import com.abhi.cmart.service.interfaces.CustomerService;
import com.abhi.cmart.utilities.CustomerSignUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void ShouldReturnAllCustomersTest() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Elon", "Musk", "elon@gmail.com", "Elon@123"));
        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(customers, customerService.findAll());
    }

    @Test
    void ShouldSaveGivenCustomerTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Elon@123");
        customerService.save(customer);
        verify(customerRepository,times(1)).save(customer);
    }

    @Test
    void ShouldDeleteGivenCustomerTest() {
        customerService.deleteCustomer(1);
        verify(customerRepository,times(1)).deleteById(1);
    }

    @Test
    void ShouldReturnCustomerWhenGivenExistingIdTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Elon@123");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        assertEquals(customer, customerService.findCustomerById(1));
    }

    @Test
    void ShouldThrowExceptionWhenGivenNonExistingIdTest() throws RuntimeException{
        Optional<Customer> customer = Optional.empty();
        when(customerRepository.findById(2)).thenReturn(customer);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            customerService.findCustomerById(2);
        });
        assertEquals("Did not find Customer Id - " + 2, thrown.getMessage());
    }

    @Test
    void ShouldEncodePasswordTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Qwerty@123");
        when(passwordEncoder.encode("Qwerty@123")).thenReturn("Qwerty@123");
        assertEquals(customer, customerService.encodePassword(customer));
    }

    @Test
    void ShouldReturnCustomerByEmailTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Qwerty@123");
        when(customerRepository.findByEmail("elon@gmail.com")).thenReturn(customer);
        assertEquals(customer, customerService.findCustomerByEmail("elon@gmail.com"));
    }

    @Test
    void ShouldReturnCustomerIdByEmailTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Qwerty@123");
        when(customerRepository.findByEmail("elon@gmail.com")).thenReturn(customer);
        assertEquals(1, customerService.getCustomerIdByEmail("elon@gmail.com"));
    }

}