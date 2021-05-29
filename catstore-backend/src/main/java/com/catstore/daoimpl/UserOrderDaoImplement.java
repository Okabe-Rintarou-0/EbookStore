package com.catstore.daoimpl;

import com.catstore.dao.UserOrderDao;
import com.catstore.entity.Book;
import com.catstore.entity.UserOrder;
import com.catstore.entity.OrderItem;
import com.catstore.repository.BookRepository;
import com.catstore.repository.UserOrderRepository;
import com.catstore.repository.OrderItemRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserOrderDaoImplement implements UserOrderDao {

    OrderItemRepository orderItemRepository;
    UserOrderRepository userOrderRepository;
    UserRepository userRepository;
    BookRepository bookRepository;

    @Autowired
    void setUserOrderRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    void setOrderRecordRepository(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<UserOrder> getAllOrders() {
        Integer userId = SessionUtil.getUserId();
        if (userId != null)
            return userOrderRepository.getAllOrders(userId);
        return null;
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return userOrderRepository.getUserOrdersByOrderId(orderId);
    }

    @Override
    public Integer addOrder(String receiver, String address, String tel) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(userId);
            userOrder.setOrderReceiver(receiver);
            userOrder.setOrderAddress(address);
            userOrder.setOrderTel(tel);
            userOrder.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));
            return userOrderRepository.saveAndFlush(userOrder).getOrderId();
        }
        return null;
    }

    @Override
    public void addOrderItem(Integer orderId, Integer bookId, Integer purchaseNumber) {
        OrderItem orderItem = new OrderItem();
        OrderItem.OrderItemId orderItemId = new OrderItem.OrderItemId();
        orderItemId.setOrderId(orderId);
        orderItemId.setBookId(bookId);
        orderItem.setOrderItemId(orderItemId);
        orderItem.setPurchaseNumber(purchaseNumber);
        orderItem.setBook(bookRepository.getBookById(bookId));
        orderItemRepository.save(orderItem);
    }
}
