package com.catstore.daoimpl;

import com.catstore.dao.ConsumptionDao;
import com.catstore.entity.Consumption;
import com.catstore.entity.User;
import com.catstore.repository.ConsumptionRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class ConsumptionDaoImplement implements ConsumptionDao {

    ConsumptionRepository consumptionRepository;
    UserRepository userRepository;

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    void setConsumptionRepository(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public void addUserConsumption(BigDecimal consumptionNumber) {
        Integer userId = SessionUtil.getUserId();
        if (userId != null) {
            System.out.println(consumptionNumber);
            Consumption consumption = new Consumption();
            consumption.setConsumptionTime(Timestamp.valueOf(LocalDateTime.now()));
            consumption.setUserId(userId);
            consumption.setConsumptionNumber(consumptionNumber);
            consumptionRepository.save(consumption);
        }
    }

    @Override
    public ArrayList<Consumption> getConsumptionsByUserId(Integer userId) {
        return consumptionRepository.getConsumptionsByUserId(userId);
    }
}
