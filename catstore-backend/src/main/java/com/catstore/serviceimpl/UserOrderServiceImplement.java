package com.catstore.serviceimpl;

import com.catstore.dao.BookDao;
import com.catstore.dao.UserDao;
import com.catstore.dao.UserOrderDao;
import com.catstore.entity.OrderRecord;
import com.catstore.entity.UserOrder;
import com.catstore.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserOrderServiceImplement implements UserOrderService {
    UserOrderDao userOrderDao;
    UserDao userDao;
    BookDao bookDao;

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
    public void addToCart(Integer bookId) {
        userOrderDao.addUserOrder("未购买", 1, bookId);
    }

    @Override
    public void placeOrder(Integer orderId) {
        System.out.println(orderId);
        userOrderDao.placeOrder(orderId);
        userDao.updateUserProperty(-bookDao.getBookById(userOrderDao.getUserOrderByOrderId(orderId).getBookId()).getBookPrice());
    }

    @Override
    public void modifyUserOrder(Integer orderId, String orderReceiver, String orderAddress, String orderTel) {
        userOrderDao.modifyUserOrder(orderId, orderReceiver, orderAddress, orderTel);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        userOrderDao.deleteUserOrderByOrderId(orderId);
    }


    @Override
    public List<Map<String, String>> getAllOrders() {
        return userOrderDao.getAllOrders();
    }

}
