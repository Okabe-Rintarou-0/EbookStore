package com.catstore.repository;

import com.catstore.entity.OrderItem;
import net.sf.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItem.OrderItemId> {

}
