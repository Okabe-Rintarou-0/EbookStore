package com.catstore.serviceimpl;

import com.catstore.dao.BookDao;
import com.catstore.dao.CartDao;
import com.catstore.dao.UserDao;
import com.catstore.dao.UserOrderDao;
import com.catstore.entity.OrderItem;
import com.catstore.entity.UserOrder;
import com.catstore.service.UserOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderServiceImplement implements UserOrderService {
    UserOrderDao userOrderDao;
    CartDao cartDao;
    UserDao userDao;
    BookDao bookDao;

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
    public boolean placeOrder(JSONObject orderItems) {
        if (orderItems != null) {
            JSONArray itemList = JSONArray.fromObject(orderItems.get("itemList"));
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            BigDecimal userProperty = userDao.getUserProperty();
            for (Object listItem : itemList) {
                JSONObject orderItem = JSONObject.fromObject(listItem);
                Integer bookId = (Integer) orderItem.get("bookId");
                BigDecimal bookPrice = bookDao.getBookById(bookId).getBookPrice();
                System.out.println("bookPrice" + bookPrice);
                Integer purchaseNumber = (Integer) (orderItem.get("purchaseNumber"));
                System.out.println("purchaseNumber" + purchaseNumber);
                totalPrice = totalPrice.add(bookPrice.multiply(BigDecimal.valueOf(purchaseNumber)));
            }
            System.out.println(totalPrice);
            if (userProperty.compareTo(totalPrice) > 0) {
                String orderAddress = orderItems.getString("orderAddress");
                String orderTel = orderItems.getString("orderTel");
                String orderReceiver = orderItems.getString("orderReceiver");
                Integer orderId = userOrderDao.addOrder(orderReceiver, orderAddress, orderTel);
                for (Object listItem : itemList) {
                    JSONObject orderItem = JSONObject.fromObject(listItem);
                    Integer bookId = (Integer) (orderItem.get("bookId"));
                    Integer purchaseNumber = (Integer) (orderItem.get("purchaseNumber"));
                    Integer cartId = (Integer) (orderItem.get("cartId"));
                    cartDao.deleteCartItem(cartId);
                    userOrderDao.addOrderItem(orderId, bookId, purchaseNumber);
                    bookDao.minusBookStockBy(bookId, purchaseNumber);
                }
                userDao.updateUserProperty(BigDecimal.valueOf(0).subtract(totalPrice)); //-totalPrice
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OrderItem> getUserOrdersByOrderId(Integer orderId) {
        return userOrderDao.getUserOrdersByOrderId(orderId);
    }


    @Override
    public ArrayList<UserOrder> getAllOrders() {
        return userOrderDao.getAllOrders();
    }
}
