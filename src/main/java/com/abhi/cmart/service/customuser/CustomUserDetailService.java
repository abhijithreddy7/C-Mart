package com.abhi.cmart.service.customuser;

import com.abhi.cmart.dao.CustomerRepository;
import com.abhi.cmart.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new UsernameNotFoundException("Account with given Email Id does not exist");
        }
        if(email.equals("admin@gmail.com")) {
            return User.withUsername(email)
                    .password(customer.getPassword())
                    .authorities("ADMIN").build();
        }
        return User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .authorities("USER").build();
    }
}