package com.catstore.repository;

import com.catstore.dao.UserOrderDao;
import com.catstore.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    @Modifying
    @Query(value = "insert into user_order (order_id,order_time,order_state,purchase_number,order_tel,order_address,order_receiver,book_id) values (?1,now(),?2,?3,?4,?5,?6,?7)", nativeQuery = true)
    void addUserOrder(Integer orderId, String orderState, Integer purchaseNumber, String orderTel, String orderAddress, String orderReceiver, Integer bookId);

    @Modifying
    @Query(value = "update user_order set order_state = '已购买' where order_id = ?1", nativeQuery = true)
    void placeOrder(Integer orderId);

    @Modifying
    @Query(value = "delete from UserOrder where orderId = ?1")
    void deleteUserOrderByOrderId(Integer orderId);

    @Modifying
    @Query(value = "update UserOrder set orderReceiver = ?2,orderAddress = ?3,orderTel = ?4 where orderId = ?1")
    void modifyUserOrder(Integer orderId, String orderReceiver, String orderAddress, String orderTel);

    @Query(value = "select * from (user_order natural join order_record natural join book) where user_id = ?1", nativeQuery = true)
    List<Map<String, String>> getAllOrders(Integer userId);

    @Query(value = "from UserOrder where orderId = ?1")
    UserOrder getUserOrderByOrderId(Integer orderId);
}
