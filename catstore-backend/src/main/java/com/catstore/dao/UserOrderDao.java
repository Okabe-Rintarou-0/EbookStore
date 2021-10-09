package com.catstore.dao;

import com.catstore.entity.UserOrder;
import com.catstore.entity.OrderItem;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public interface UserOrderDao {

    ArrayList<UserOrder> getAllOrders(Integer userId);

    ArrayList<UserOrder> getAllOrdersForManager();

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    Integer addOrder(Integer userId, String receiver, String address, String tel);

    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);

    ArrayList<UserOrder> getOrdersInRangeForManager(Date start, Date end);

    ArrayList<UserOrder> getOrdersInRange(Date start, Date end);
}
