package com.abhi.cmart.controller;

import com.abhi.cmart.service.interfaces.CustomerService;
import com.abhi.cmart.utilities.CustomerSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String showFormForCustomerSignup(Model model) {
        CustomerSignUp customer = new CustomerSignUp();
        model.addAttribute("customer", customer);
        model.addAttribute("error", false);
        return "customer-signup";
    }

    @PostMapping("/signup")
    public String addCustomer(@Valid @ModelAttribute("customer") CustomerSignUp customer, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "customer-signup";
        }
        else  if(!customerService.isNewEmail(customer.getEmail())) {
            model.addAttribute("error", true);
            model.addAttribute("signedUp", false);
            return "customer-signup";
        }
        customer.setId(0);
        model.addAttribute("error", false);
        model.addAttribute("signedUp", true);
        customerService.save(customer);
        return "customer-signin";
    }
}