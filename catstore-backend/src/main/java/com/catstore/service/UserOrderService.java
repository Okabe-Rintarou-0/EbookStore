package com.catstore.service;

import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public interface UserOrderService {

    boolean placeOrder(JSONObject orderItems);

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    ArrayList<UserOrder> getAllOrders();

    ArrayList<UserOrder> getAllOrdersForManager();

    ArrayList<UserOrder> getOrdersInRange(Date start, Date end);
}
