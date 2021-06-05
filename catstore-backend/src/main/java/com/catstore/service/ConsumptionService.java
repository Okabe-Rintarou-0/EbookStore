package com.catstore.service;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import java.util.Date;

public interface ConsumptionService {
    JSONArray getAllUsersAndTheirConsumption(Date begin, Date end);

    JSONArray getConsumptionsGroupByBooks(Date begin, Date end);
}
