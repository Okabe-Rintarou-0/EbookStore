package com.catstore.dtoimpl;

import com.catstore.dao.ConsumptionDao;
import com.catstore.dao.UserDao;
import com.catstore.dao.UserOrderDao;
import com.catstore.dto.ConsumptionDto;
import com.catstore.entity.*;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class ConsumptionDtoImplement implements ConsumptionDto {
    ConsumptionDao consumptionDao;
    UserOrderDao userOrderDao;
    UserDao userDao;

    @Autowired
    void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    @Autowired
    void setConsumptionDao(ConsumptionDao consumptionDao) {
        this.consumptionDao = consumptionDao;
    }

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public JSONArray getAllUsersAndTheirConsumption(Date begin, Date end) {
        JSONArray jsonArray = new JSONArray();
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            JSONObject jsonObject = new JSONObject();
            Integer userId = user.getUserId();
            jsonObject.put("userId", userId);
            jsonObject.put("username", user.getUsername());
            jsonObject.put("userIcon", user.getUserIcon());
            ArrayList<Consumption> consumptionArray = begin == null || end == null ?
                    consumptionDao.getConsumptionsByUserId(userId) :
                    consumptionDao.getConsumptionsByUserIdInRange(userId, begin, end);
            JSONArray consumptionJsonArray = new JSONArray();
            BigDecimal totalConsumption = BigDecimal.ZERO;
            for (Consumption consumption : consumptionArray) {
                totalConsumption = totalConsumption.add(consumption.getConsumptionNumber());
                JSONObject tmpJson = new JSONObject();
                tmpJson.put("consumptionTime", consumption.getConsumptionTime().toString());
                tmpJson.put("consumptionNumber", consumption.getConsumptionNumber());
                consumptionJsonArray.add(tmpJson);
            }
            jsonObject.put("sum", totalConsumption);
            jsonObject.put("consumptions", consumptionJsonArray);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray getConsumptionsGroupByBooks(Date begin, Date end) {
        JSONArray jsonArray = new JSONArray();
        ArrayList<UserOrder> userOrders = begin == null || end == null ?
                userOrderDao.getAllOrders(SessionUtil.getUserId()) :
                userOrderDao.getOrdersInRange(begin, end);
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Map<Integer, JSONArray> bookOrderMap = new HashMap<>();
        for (UserOrder userOrder : userOrders) {
            Set<OrderItem> orderItems = userOrder.getOrders();
            for (OrderItem orderItem : orderItems) {
                Book book = orderItem.getBook();
                Integer bookId = book.getBookId();
                if (bookOrderMap.get(bookId) == null) {
                    bookOrderMap.put(bookId, new JSONArray());
                    bookArrayList.add(book);
                }
                JSONObject orderInfo = new JSONObject();
                orderInfo.put("orderTime", userOrder.getOrderTime().toString());
                orderInfo.put("purchaseNumber", orderItem.getPurchaseNumber());
                bookOrderMap.get(bookId).add(orderInfo);
            }
        }
        for (Book book : bookArrayList) {
            JSONObject consumption = new JSONObject();
            consumption.put("bookPrice", book.getBookPrice());
            consumption.put("bookTitle", book.getBookTitle());
            consumption.put("bookCover", book.getBookCover());
            consumption.put("consumptions", bookOrderMap.get(book.getBookId()));
            jsonArray.add(consumption);
        }
        return jsonArray;
    }
}
