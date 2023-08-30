package com.catstore.serviceimpl;

import com.catstore.dto.ConsumptionDto;
import com.catstore.service.ConsumptionService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsumptionServiceImplement implements ConsumptionService {
    ConsumptionDto consumptionDto;

    @Autowired
    void setConsumptionDto(ConsumptionDto consumptionDto) {
        this.consumptionDto = consumptionDto;
    }

    @Override
    public JSONArray getAllUsersAndTheirConsumption(Date begin, Date end) {
        return consumptionDto.getAllUsersAndTheirConsumption(begin, end);
    }

    @Override
    public JSONArray getConsumptionsGroupByBooks(Date begin, Date end) {
        return consumptionDto.getConsumptionsGroupByBooks(begin, end);
    }
}
