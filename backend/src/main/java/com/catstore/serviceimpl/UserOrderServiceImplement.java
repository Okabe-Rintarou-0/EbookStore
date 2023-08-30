package com.catstore.serviceimpl;

import com.catstore.dao.*;
import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import com.catstore.model.OrderInfo;
import com.catstore.model.OrderItemInfo;
import com.catstore.service.UserOrderService;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserOrderServiceImplement implements UserOrderService {
    @Autowired
    UserOrderDao userOrderDao;
    @Autowired
    CartDao cartDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    ConsumptionDao consumptionDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
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
            //Transaction Required
            Integer orderId = userOrderDao.addOrder(userId, orderReceiver, orderAddress, orderTel);
            System.out.println("orderId: " + orderId);
            for (OrderItemInfo item : items) {
                Integer bookId = item.bookId;
                Integer purchaseNumber = item.purchaseNumber;
                Integer cartId = item.cartId;
                //Transaction Required
                cartDao.deleteCartItem(cartId);
                //Transaction Required
                userOrderDao.addOrderItem(orderId, bookId, purchaseNumber);
                //Transaction Required
                bookDao.adjustStock(bookId, purchaseNumber);
            }
            //Transaction Required
            consumptionDao.addUserConsumption(totalPrice);
            //Transaction Required
            userDao.updateUserProperty(userId, totalPrice.negate()); //-totalPrice
        }
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return userOrderDao.getUserOrdersByOrderId(orderId);
    }

    @Override
    public ArrayList<UserOrder> getAllOrders() {
        return userOrderDao.getAllOrders(SessionUtil.getUserId());
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
