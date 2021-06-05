package com.catstore.repository;

import com.catstore.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.ArrayList;

@Transactional
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    @Query(value = "from UserOrder where userId = ?1")
    ArrayList<UserOrder> getAllOrders(Integer userId);

    @Query(value = "from UserOrder")
    ArrayList<UserOrder> getAllOrdersForManager();

    @Query(value = "from UserOrder where orderTime between ?1 and ?2")
    ArrayList<UserOrder> getAllOrdersInRange(Date start, Date end);

    @Query(value = "from UserOrder where orderTime between ?2 and ?3 and userId = ?1")
    ArrayList<UserOrder> getOrdersInRange(Integer userId, Date start, Date end);
}
