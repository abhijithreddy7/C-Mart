package com.abhi.cmart.controller;

import com.abhi.cmart.entity.Item;
import com.abhi.cmart.entity.Order;
import com.abhi.cmart.service.interfaces.CustomerService;
import com.abhi.cmart.service.interfaces.ItemService;
import com.abhi.cmart.service.interfaces.OrderService;
import com.abhi.cmart.utilities.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/admin/orders")
    public String getOrders(Model model) {
        List<CustomerOrder> customerOrders = getCustomerOrders(orderService.findAll());
        model.addAttribute("orders", customerOrders);
        return "customer-orders";
    }

    @PostMapping("/admin/orders")
    public String getAllOrders(Model model) {
        return getOrders(model);
    }


    @GetMapping("/user/orders")
    public String getCustomerOrders(Model model) {
        List<CustomerOrder> orders = getCustomerOrders(orderService.getCustomerOrders(customerService.
                getCustomerIdByEmail(customerService.
                        getCurrentUserEmail())));
        if(orders != null && orders.size()!=0) {
            model.addAttribute("orders", orders);
        }
        else {
            model.addAttribute("orders", null);
        }
        return "customer-orders";
    }

    @PostMapping("/user/orders")
    public String customerOrders(Model model) {
       return getCustomerOrders(model);
    }


    @GetMapping("/user/cart")
    public String getCartItems(Model model) {
        Order cart = orderService.findCartOrder(customerService.getCustomerIdByEmail(customerService.getCurrentUserEmail()));
        CustomerOrder customerCart = new CustomerOrder(cart);
        List cartItems = null;
        if(customerCart != null) {
            cartItems = customerCart.getItems();
        }
        model.addAttribute("cartItems",customerCart.getItems());
        model.addAttribute("cartCost", customerCart.getCost());
        return "cart";
    }

    @PostMapping("/user/cart")
    public String cartItems(Model model) {
        return getCartItems(model);
    }

    @PostMapping("/user/order/placeOrder/{itemId}")
    public String addToCart(@PathVariable int itemId, Model model) {
        String customerEmail = customerService.getCurrentUserEmail();
        int customerId = customerService.getCustomerIdByEmail(customerEmail);
        List<Order> cartItems = orderService.getCustomerCart(customerId);
        if(cartItems == null || cartItems.size() == 0) {
            orderService.createOrder(customerId);
            cartItems = orderService.getCustomerCart(customerId);
        }
        Order cart = cartItems.get(0);
        if(isItemPresentInCart(cart, itemId)) {
            model.addAttribute("isPresent", isItemPresentInCart(cart, itemId));
            return getCartItems(model);
        }
        Item item = itemService.findItemById(itemId);
        cart.addItem(item);
        orderService.save(cart);
        return getCartItems(model);
    }

    @GetMapping("/user/order/placeOrder/{itemId}")
    public String addItemToCart(@PathVariable int itemId, Model model) {
        return addToCart(itemId, model);
    }

    public boolean isItemPresentInCart(Order cart, int itemId) {
        for(Item item: cart.getItems()) {
            if(item.getId() == itemId)
                return true;
        }
        return false;
    }

    @PostMapping("/user/cart/delete/{itemId}")
    public String deleteItemFromCart(@PathVariable int itemId, Model model) {
        String customerEmail = customerService.getCurrentUserEmail();
        int customerId = customerService.getCustomerIdByEmail(customerEmail);
        List<Order> cartItems = orderService.getCustomerCart(customerId);
        Order cart = cartItems.get(0);
        int itemIndex = getItemIndexFromCart(cart, itemId);
        if(itemIndex == -1) {
            throw new RuntimeException("Item id not found - " + itemId);
        }
        List<Item> itemList = cart.getItems();
        itemList.remove(itemIndex);
        cart.setItems(itemList);
        orderService.save(cart);
        return getCartItems(model);
    }

    @PostMapping("/user/placeOrder")
    public String placeOrder(Model model) {
        String customerEmail = customerService.getCurrentUserEmail();
        int customerId = customerService.getCustomerIdByEmail(customerEmail);
        List<Order> cartItems = orderService.getCustomerCart(customerId);
        if(cartItems == null || cartItems.size() == 0) {
            model.addAttribute("cartItems",null);
            return "cart";
        }
        Order cart = cartItems.get(0);
        cart.setPlaced(true);
        orderService.save(cart);
        return getCartItems(model);
    }

    @GetMapping("/user/placeOrder")
    public String placeCartOrder(Model model) {
        return placeOrder(model);
    }

    int getItemIndexFromCart(Order cart, int itemId) {
        List<Item> itemList = cart.getItems();
        for(int i=0; i<itemList.size(); i++) {
            if(itemList.get(i).getId() == itemId) {
                return i;
            }
        }
        return -1;
    }

    private List<CustomerOrder> getCustomerOrders(List<Order> orders) {
        List<CustomerOrder> customerOrders = new ArrayList<>();
        for(Order order: orders) {
            customerOrders.add(new CustomerOrder(order));
        }
        return customerOrders;
    }
}