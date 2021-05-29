package com.catstore.dao;

import com.catstore.entity.UserOrder;
import com.catstore.entity.OrderItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface UserOrderDao {

    ArrayList<UserOrder> getAllOrders();

    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    Integer addOrder(String receiver, String address, String tel);

    void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber);
}
