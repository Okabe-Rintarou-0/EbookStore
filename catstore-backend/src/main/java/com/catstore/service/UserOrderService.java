package com.catstore.service;

import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import com.catstore.model.OrderInfo;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public interface UserOrderService {

    void placeOrder(OrderInfo orderInfo);

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    ArrayList<UserOrder> getAllOrders();

    ArrayList<UserOrder> getAllOrdersForManager();

    ArrayList<UserOrder> getOrdersInRange(Date start, Date end);

    ArrayList<UserOrder> getOrdersInRangeForManager(Date start, Date end);
}
