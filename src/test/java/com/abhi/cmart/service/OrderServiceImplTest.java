package com.abhi.cmart.service;

import com.abhi.cmart.dao.CustomerRepository;
import com.abhi.cmart.dao.OrderRepository;
import com.abhi.cmart.entity.Category;
import com.abhi.cmart.entity.Customer;
import com.abhi.cmart.entity.Item;
import com.abhi.cmart.entity.Order;
import com.abhi.cmart.service.interfaces.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void ShouldReturnAllOrdersWhenHasOrdersTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1,true, customer, items);
        orders.add(order);
        when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orders, orderService.findAll());
    }

    @Test
    void ShouldReturnNullWhenHasNoOrdersTest() {
        List<Order> orders = null;
        when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orders, orderService.findAll());
    }

    @Test
    void ShouldReturnOrderWhenGivenExistingIdTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1,true, customer, items);
        orders.add(order);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        assertEquals(order, orderService.findOrderById(1));
    }

    @Test
    void ShouldThrowExceptionWhenGivenNonExistingIdTest() throws RuntimeException{
        Optional<Order> order = Optional.empty();
        when(orderRepository.findById(2)).thenReturn(order);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            orderService.findOrderById(2);
        });
        assertEquals("Did not find Order Id - " + 2, thrown.getMessage());
    }

    @Test
    void ShouldReturnCustomerOrdersTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1,true, customer, items);
        orders.add(order);
        when(orderRepository.findByCustomerIdAndIsPlaced(1,true)).thenReturn(orders);
        assertEquals(orders, orderService.getCustomerOrders(1));
    }

    @Test
    void ShouldReturnCustomerCartTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1,false, customer, items);
        orders.add(order);
        when(orderRepository.findByCustomerIdAndIsPlaced(1,false)).thenReturn(orders);
        assertEquals(orders, orderService.getCustomerCart(1));
    }

    @Test
    void ShouldSaveGivenOrderTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        Order order = new Order(1,false, customer, items);
        orderService.save(order);
        verify(orderRepository,times(1)).save(order);
    }

    @Test
    void ShouldFindCartOrderByIdTest() {
        List<Item> items = new ArrayList<>();
        Category category = new Category(1, "Action");
        items.add(new Item(1, "Mount Hua Sect", "Lico", 20, 15, 5, 95, "Colour", 6, "PaperBack", 1200, "'https://cover.nep.li/cover/Return-of-the-Blossoming-Blade.jpg'", category));
        Customer customer = new Customer(1,"Elon","Musk","elon@gmail.com","Elon@123");
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1,false, customer, items);
        orders.add(order);
        when(orderRepository.findByCustomerIdAndIsPlaced(1, false)).thenReturn(orders);
        assertEquals(order, orderService.findCartOrder(1));
    }


}