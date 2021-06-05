package com.catstore.dao;

import com.catstore.entity.Consumption;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public interface ConsumptionDao {
    void addUserConsumption(BigDecimal consumptionNumber);

    ArrayList<Consumption> getConsumptionsByUserId(Integer userId);

    ArrayList<Consumption> getConsumptionsByUserIdInRange(Integer userId, Date begin, Date end);

}
