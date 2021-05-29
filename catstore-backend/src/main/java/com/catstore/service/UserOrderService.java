package com.catstore.service;

import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserOrderService {

    boolean placeOrder(JSONObject orderItems);

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    ArrayList<UserOrder> getAllOrders();

}
