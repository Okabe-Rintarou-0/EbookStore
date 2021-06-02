package com.catstore.repository;

import com.catstore.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {

    @Query(value = "from Consumption where userId = ?1 order by consumptionTime")
    ArrayList<Consumption> getConsumptionsByUserId(Integer userId);
}
