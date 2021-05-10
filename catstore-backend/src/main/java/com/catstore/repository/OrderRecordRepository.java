package com.catstore.repository;

import com.catstore.entity.OrderRecord;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface OrderRecordRepository extends JpaRepository<OrderRecord, Integer> {
    @Modifying
    @Query(value = "insert into order_record (order_id,user_id) values (?1,?2)", nativeQuery = true)
    void addOrderRecord(Integer orderId, Integer userId);

    @Modifying
    @Query(value = "delete from OrderRecord where orderId = ?1")
    void deleteOrderRecordByOrderId(Integer orderId);

    @Query(value = "from OrderRecord where orderId = ?1")
    OrderRecord getOrderRecordByOrderId(Integer orderId);

    @Query(value = "select max(user_order.order_id) from user_order", nativeQuery = true)
    Integer getMaxOrderId();
}
