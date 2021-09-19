package com.catstore.serviceimpl;

import com.catstore.dao.*;
import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import com.catstore.model.OrderInfo;
import com.catstore.model.OrderItemInfo;
import com.catstore.service.UserOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOrderServiceImplement implements UserOrderService {
    UserOrderDao userOrderDao;
    CartDao cartDao;
    UserDao userDao;
    BookDao bookDao;
    ConsumptionDao consumptionDao;

    @Autowired
    void setConsumptionDao(ConsumptionDao consumptionDao) {
        this.consumptionDao = consumptionDao;
    }

    @Autowired
    void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Autowired
    void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    @Transactional
    public void placeOrder(OrderInfo orderInfo) {
        List<OrderItemInfo> items = orderInfo.items;
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        Integer userId = orderInfo.userId;
        BigDecimal userProperty = orderInfo.userProperty;
        System.out.println("userProperty: " + userProperty);
        for (OrderItemInfo item : items) {
            Integer bookId = item.bookId;
            BigDecimal bookPrice = bookDao.getBookById(bookId).getBookPrice();
            System.out.println("bookPrice: " + bookPrice);
            Integer purchaseNumber = item.purchaseNumber;
            System.out.println("purchaseNumber: " + purchaseNumber);
            totalPrice = totalPrice.add(bookPrice.multiply(BigDecimal.valueOf(purchaseNumber)));
        }
        System.out.println("total:" + totalPrice);
        if (userProperty.compareTo(totalPrice) > 0) {
            String orderAddress = orderInfo.address;
            String orderTel = orderInfo.tel;
            String orderReceiver = orderInfo.receiver;
            Integer orderId = userOrderDao.addOrder(userId, orderReceiver, orderAddress, orderTel);
            System.out.println("orderId: " + orderId);
            for (OrderItemInfo item : items) {
                Integer bookId = item.bookId;
                Integer purchaseNumber = item.purchaseNumber;
                Integer cartId = item.cartId;
                cartDao.deleteCartItem(cartId);
                userOrderDao.addOrderItem(orderId, bookId, purchaseNumber);
                bookDao.placeOrder(bookId, purchaseNumber);
            }
            consumptionDao.addUserConsumption(totalPrice);
            userDao.updateUserProperty(userId, totalPrice.negate()); //-totalPrice
        }
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return userOrderDao.getUserOrdersByOrderId(orderId);
    }


    @Override
    public ArrayList<UserOrder> getAllOrders() {
        return userOrderDao.getAllOrders();
    }

    @Override
    public ArrayList<UserOrder> getAllOrdersForManager() {
        return userOrderDao.getAllOrdersForManager();
    }

    @Override
    public ArrayList<UserOrder> getOrdersInRange(Date start, Date end) {
        return userOrderDao.getOrdersInRange(start, end);
    }

    @Override
    public ArrayList<UserOrder> getOrdersInRangeForManager(Date start, Date end) {
        return userOrderDao.getOrdersInRangeForManager(start, end);
    }
}
