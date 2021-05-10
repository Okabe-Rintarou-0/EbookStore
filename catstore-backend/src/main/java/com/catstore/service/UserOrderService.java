package com.catstore.service;

import java.util.List;
import java.util.Map;

public interface UserOrderService {

    void addToCart(Integer bookId);

    void placeOrder(Integer orderId);

    void modifyUserOrder(Integer orderId, String orderReceiver, String orderAddress, String orderTel);

    void deleteOrder(Integer orderId);

    List<Map<String, String>> getAllOrders();
}
