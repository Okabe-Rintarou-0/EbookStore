package com.catstore.dao;

import com.catstore.entity.OrderRecord;
import com.catstore.entity.UserOrder;

import java.util.List;
import java.util.Map;


public interface UserOrderDao {

    void addUserOrder(String orderState, Integer purchaseNumber, Integer bookId);

    void placeOrder(Integer orderId);

    void deleteUserOrderByOrderId(Integer orderId);

    void modifyUserOrder(Integer orderId, String orderReceiver, String orderAddress, String orderTel);

    UserOrder getUserOrderByOrderId(Integer orderId);

    OrderRecord getOrderRecordByOrderId(Integer orderId);

    List<Map<String, String>> getAllOrders();
}
