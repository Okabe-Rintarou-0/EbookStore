package com.catstore.serviceimpl;

import com.catstore.dto.ConsumptionDto;
import com.catstore.service.ConsumptionService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionServiceImplement implements ConsumptionService {
    ConsumptionDto consumptionDto;

    @Autowired
    void setConsumptionDto(ConsumptionDto consumptionDto) {
        this.consumptionDto = consumptionDto;
    }

    @Override
    public JSONArray getAllUsersAndTheirConsumption() {
        return consumptionDto.getAllUsersAndTheirConsumption();
    }
}
