package com.catstore.repository;

import com.catstore.entity.OrderItem;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query(value = "from OrderItem where orderId = ?1")
    List<OrderItem> getUserOrdersByOrderId(Integer orderId);
}
