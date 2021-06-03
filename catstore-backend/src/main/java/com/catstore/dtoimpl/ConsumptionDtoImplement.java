package com.catstore.dtoimpl;

import com.catstore.dao.ConsumptionDao;
import com.catstore.dao.UserDao;
import com.catstore.dto.ConsumptionDto;
import com.catstore.entity.Consumption;
import com.catstore.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ConsumptionDtoImplement implements ConsumptionDto {
    ConsumptionDao consumptionDao;
    UserDao userDao;

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
}
