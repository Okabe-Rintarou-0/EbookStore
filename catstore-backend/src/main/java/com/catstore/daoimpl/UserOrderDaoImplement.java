package com.catstore.daoimpl;

import com.catstore.dao.UserOrderDao;
import com.catstore.entity.OrderRecord;
import com.catstore.entity.User;
import com.catstore.entity.UserOrder;
import com.catstore.repository.OrderRecordRepository;
import com.catstore.repository.UserOrderRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Repository
public class UserOrderDaoImplement implements UserOrderDao {

    UserOrderRepository userOrderRepository;
    OrderRecordRepository orderRecordRepository;
    UserRepository userRepository;

    @Autowired
    void setUserOrderRepository(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    @Autowired
    void setOrderRecordRepository(OrderRecordRepository orderRecordRepository) {
        this.orderRecordRepository = orderRecordRepository;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUserOrder(String orderState, Integer purchaseNumber, Integer bookId) {
        Integer userId = SessionUtil.getUserId();
        if (userId == null) return;
        System.out.print("userId ");
        System.out.println(userId);
        User user = userRepository.getUserById(userId);
        Integer maxOrderId = orderRecordRepository.getMaxOrderId();
        int orderId;
        if (maxOrderId == null) orderId = 1;
        else orderId = maxOrderId + 1;
        orderRecordRepository.addOrderRecord(orderId, userId);
        userOrderRepository.addUserOrder(orderId, orderState, purchaseNumber, user.getUserTel(), user.getUserAddress(), user.getUsername(), bookId);
    }

    @Override
    public void placeOrder(Integer orderId) {
        userOrderRepository.placeOrder(orderId);
    }

    @Override
    public void deleteUserOrderByOrderId(Integer orderId) {
        userOrderRepository.deleteUserOrderByOrderId(orderId);
        orderRecordRepository.deleteOrderRecordByOrderId(orderId);
    }

    @Override
    public void modifyUserOrder(Integer orderId, String orderReceiver, String orderAddress, String orderTel) {
        userOrderRepository.modifyUserOrder(orderId, orderReceiver, orderAddress, orderTel);
    }

    @Override
    public UserOrder getUserOrderByOrderId(Integer orderId) {
        return userOrderRepository.getUserOrderByOrderId(orderId);
    }

    @Override
    public OrderRecord getOrderRecordByOrderId(Integer orderId) {
        return orderRecordRepository.getOrderRecordByOrderId(orderId);
    }

    @Override
    public List<Map<String, String>> getAllOrders() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return userOrderRepository.getAllOrders(userId);
        return null;
    }
}
