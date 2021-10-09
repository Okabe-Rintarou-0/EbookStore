package com.catstore.controller;

import com.catstore.service.ConsumptionService;
import com.catstore.constants.Constant;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class ConsumptionController {

    ConsumptionService consumptionService;

    @Autowired
    void setConsumptionService(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @GetMapping ("/manager/getConsumption")
    JSONArray getAllUsersAndTheirConsumption() {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            return consumptionService.getAllUsersAndTheirConsumption(null, null);
        }
        return null;
    }

    @PostMapping("/manager/getConsumptionInRange")
    JSONArray getConsumptionInRange(@RequestBody ArrayList<Date> startNEndDates) {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            if (startNEndDates != null && startNEndDates.size() == 2)
                return consumptionService.getAllUsersAndTheirConsumption(startNEndDates.get(0), startNEndDates.get(1));
            else return null;
        }
        return null;
    }

    @PostMapping("/getConsumptionsGroupByBooks")
    JSONArray getConsumptionsGroupByBooks(@RequestBody ArrayList<Date> startNEndDates) {
        if (startNEndDates != null && startNEndDates.size() == 2)
            return consumptionService.getConsumptionsGroupByBooks(startNEndDates.get(0), startNEndDates.get(1));
        return consumptionService.getConsumptionsGroupByBooks(null, null);
    }
}
