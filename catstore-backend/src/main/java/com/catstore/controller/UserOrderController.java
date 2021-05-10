package com.catstore.controller;

import com.catstore.dao.UserOrderDao;
import com.catstore.entity.UserOrder;
import com.catstore.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserOrderController {

    UserOrderService userOrderService;

    @Autowired
    void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @RequestMapping("/addToCart")
    void addToCart(@RequestParam("bookId") Integer bookId) {
        userOrderService.addToCart(bookId);
    }

    @RequestMapping("/placeOrder")
    void placeOrder(@RequestParam("orderId") Integer orderId) {
        userOrderService.placeOrder(orderId);
    }

    @RequestMapping("/deleteOrder")
    void deleteOrder(@RequestParam("orderId") Integer orderId) {
        userOrderService.deleteOrder(orderId);
    }

    @RequestMapping("/modifyOrder")
    void modifyUserOrder(@RequestBody Map<String, String> params, @RequestParam("orderId") Integer orderId) {
        String orderReceiver = (String) params.get("orderReceiver");
        String orderAddress = (String) params.get("orderAddress");
        String orderTel = (String) params.get("orderTel");
        System.out.println(orderId);
        userOrderService.modifyUserOrder(orderId, orderReceiver, orderAddress, orderTel);
    }

    @RequestMapping("/getAllOrders")
    List<Map<String, String>> getAllOrders() {
        return userOrderService.getAllOrders();
    }
}