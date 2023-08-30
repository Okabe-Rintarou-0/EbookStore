package com.catstore.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.catstore.constants.RedisKeys;
import com.catstore.dao.UserOrderDao;
import com.catstore.entity.Book;
import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import com.catstore.repository.BookRepository;
import com.catstore.repository.OrderItemRepository;
import com.catstore.repository.UserOrderRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.redisUtils.RedisUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserOrderDaoImplement implements UserOrderDao {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ArrayList<UserOrder> getAllOrders(Integer userId) {
        if (userId == null) return null;
        String redisKey = RedisKeys.OrderKey + ":" + userId;
        List<UserOrder> userOrders = redisUtil.getWholeList(redisKey, UserOrder.class);
        if (userOrders.size() > 0) {
            System.out.println("Fetch orders from redis.");
        } else {
            System.out.println("Fetch orders from db.");
            userOrders = userOrderRepository.getAllOrders(userId);
            redisUtil.rpushObj(redisKey, userOrders);
            redisUtil.expire(redisKey, 300);
        }
        return (ArrayList<UserOrder>) userOrders;
    }

    @Override
    public ArrayList<UserOrder> getAllOrdersForManager() {
        return userOrderRepository.getAllOrdersForManager();
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return orderItemRepository.getUserOrdersByOrderId(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Integer addOrder(Integer userId, String receiver, String address, String tel) {
        Integer orderId = null;
        if (userId != null) {
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(userId);
            userOrder.setOrderReceiver(receiver);
            userOrder.setOrderAddress(address);
            userOrder.setOrderTel(tel);
            userOrder.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));
            orderId = userOrderRepository.saveAndFlush(userOrder).getOrderId();
            redisUtil.del(RedisKeys.OrderKey + ":" + userId);
        }
        return orderId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setPurchaseNumber(purchaseNumber);
        orderItem.setBookId(bookId);
        Book book = bookRepository.getBookById(bookId);
        orderItem.setBook(book);
        orderItemRepository.save(orderItem);
    }

    @Override
    public ArrayList<UserOrder> getOrdersInRangeForManager(Date start, Date end) {
        return userOrderRepository.getAllOrdersInRange(start, end);
    }

    @Override
    public ArrayList<UserOrder> getOrdersInRange(Date start, Date end) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            return userOrderRepository.getOrdersInRange(userId, start, end);
        }
        return null;
    }
}
