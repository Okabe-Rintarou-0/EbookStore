package com.catstore.controller;

import com.catstore.service.ConsumptionService;
import com.catstore.utils.Constant;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumptionController {

    ConsumptionService consumptionService;

    @Autowired
    void setConsumptionService(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @RequestMapping("/manager/getConsumption")
    JSONArray getAllUsersAndTheirConsumption() {
        Integer userIdentity = SessionUtil.getUserIdentity();
        if (userIdentity != null && userIdentity.equals(Constant.MANAGER)) {
            return consumptionService.getAllUsersAndTheirConsumption();
        }
        return null;
    }
}
