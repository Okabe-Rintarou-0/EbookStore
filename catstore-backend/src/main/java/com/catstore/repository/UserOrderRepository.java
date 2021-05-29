package com.catstore.repository;

import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {
    @Query(value = "from OrderItem where orderItemId.orderId = ?1")
    List<OrderItem> getUserOrdersByOrderId(Integer orderId);

    @Query(value = "from UserOrder")
    ArrayList<UserOrder> getAllOrders(Integer userId);
}
