package com.abhi.cmart.service;

import com.abhi.cmart.dao.CustomerRepository;
import com.abhi.cmart.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomUserDetailServiceTest {

    @Autowired
    UserDetailsService customUserDetailsService;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void ShouldLoadUserByUsernameTest() {
        Customer customer = new Customer(1, "Elon", "Musk", "elon@gmail.com", "Elon@123");
        UserDetails user = User.withUsername("elon@gmail.com")
                .password(customer.getPassword())
                .authorities("USER").build();
        when(customerRepository.findByEmail("elon@gmail.com")).thenReturn(customer);
        assertEquals(user, customUserDetailsService.loadUserByUsername("elon@gmail.com"));
    }

    @Test
    void ShouldLoadAdminByUsernameTest() {
        Customer customer = new Customer(1, "Furniture", "Store", "admin@gmail.com", "Admin@123");
        UserDetails user = User.withUsername("admin@gmail.com")
                .password(customer.getPassword())
                .authorities("ADMIN").build();
        when(customerRepository.findByEmail("admin@gmail.com")).thenReturn(customer);
        assertEquals(user, customUserDetailsService.loadUserByUsername("admin@gmail.com"));
    }

    @Test
    void ShouldThrowExceptionWhenUserDoesNotExistTest() throws RuntimeException{
        Optional<Customer> customer = Optional.empty();
        when(customerRepository.findByEmail("bill@gmail.com")).thenReturn(null);
        RuntimeException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("bill@gmail.com");
        });
        Assertions.assertEquals("Account with given Email Id does not exist", thrown.getMessage());
    }

}