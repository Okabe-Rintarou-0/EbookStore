package com.catstore.dto;

import net.sf.json.JSONArray;

import java.util.Date;

public interface ConsumptionDto {
    JSONArray getAllUsersAndTheirConsumption(Date begin, Date end);

    JSONArray getConsumptionsGroupByBooks(Date begin, Date end);
}
