package com.catstore.controller;

import com.catstore.entity.UserOrder;
import com.catstore.service.UserOrderService;
import com.catstore.model.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class UserOrderController {

    UserOrderService userOrderService;

    @Autowired
    void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @PostMapping("/placeOrder")
    Message placeOrder(@RequestBody JSONObject orderItems) {
        System.out.println(orderItems.toString());
        if (userOrderService.placeOrder(orderItems))
            return MessageUtil.createMessage(MessageUtil.PURCHASE_SUCCESS_CODE, MessageUtil.PURCHASE_SUCCESS_MSG);
        return MessageUtil.createMessage(MessageUtil.PURCHASE_FAIL_CODE, MessageUtil.PURCHASE_FAIL_MSG);
    }

    @GetMapping("/getAllOrders")
    ArrayList<UserOrder> getAllOrders() {
        return userOrderService.getAllOrders();
    }

    @GetMapping("/manager/getAllOrders")
    ArrayList<UserOrder> getAllOrdersForManager() {
        return userOrderService.getAllOrdersForManager();
    }

    @PostMapping("/manager/searchOrders")
    ArrayList<UserOrder> searchOrdersForManager(@RequestBody ArrayList<Date> startNEndDates) {
        System.out.println(startNEndDates.toString());
        if (startNEndDates.size() == 2)
            return userOrderService.getOrdersInRangeForManager(startNEndDates.get(0), startNEndDates.get(1));
        return null;
    }

    @PostMapping("/searchOrders")
    ArrayList<UserOrder> searchOrders(@RequestBody ArrayList<Date> startNEndDates) {
        if (startNEndDates != null && startNEndDates.size() == 2) {
            Date start = startNEndDates.get(0);
            Date end = startNEndDates.get(1);
            if (start != null && end != null)
                return userOrderService.getOrdersInRange(start, end);
        }
        return userOrderService.getAllOrders();
    }

}
