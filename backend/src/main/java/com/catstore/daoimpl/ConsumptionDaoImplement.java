package com.catstore.daoimpl;

import com.catstore.dao.ConsumptionDao;
import com.catstore.entity.Consumption;
import com.catstore.repository.ConsumptionRepository;
import com.catstore.repository.UserRepository;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
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

    @Override
    public ArrayList<Consumption> getConsumptionsByUserIdInRange(Integer userId, Date begin, Date end) {
        return consumptionRepository.getConsumptionsByUserIdInRange(userId, begin, end);
    }
}
