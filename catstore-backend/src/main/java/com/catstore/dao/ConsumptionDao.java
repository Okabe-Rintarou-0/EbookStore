package com.catstore.dao;

import com.catstore.entity.Consumption;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ConsumptionDao {
    void addUserConsumption(BigDecimal consumptionNumber);

    ArrayList<Consumption> getConsumptionsByUserId(Integer userId);
}
