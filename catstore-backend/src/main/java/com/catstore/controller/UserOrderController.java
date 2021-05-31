package com.catstore.controller;

import com.catstore.entity.UserOrder;
import com.catstore.service.UserOrderService;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class UserOrderController {

    UserOrderService userOrderService;

    @Autowired
    void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @RequestMapping("/placeOrder")
    Message placeOrder(@RequestBody JSONObject orderItems) {
        System.out.println(orderItems.toString());
        if (userOrderService.placeOrder(orderItems))
            return MessageUtil.createMessage(MessageUtil.PURCHASE_SUCCESS_CODE, MessageUtil.PURCHASE_SUCCESS_MSG);
        return MessageUtil.createMessage(MessageUtil.PURCHASE_FAIL_CODE, MessageUtil.PURCHASE_FAIL_MSG);
    }

    @RequestMapping("/getAllOrders")
    ArrayList<UserOrder> getAllOrders() {
        return userOrderService.getAllOrders();
    }

    @RequestMapping("/manager/getAllOrders")
    ArrayList<UserOrder> getAllOrdersForManager() {
        return userOrderService.getAllOrdersForManager();
    }

    @RequestMapping("/manager/searchOrders")
    ArrayList<UserOrder> searchOrdersForManager(@RequestBody ArrayList<Date> startNEndDates) {
        System.out.println(startNEndDates.toString());
        if (startNEndDates.size() == 2)
            return userOrderService.getOrdersInRange(startNEndDates.get(0), startNEndDates.get(1));
        return null;
    }
}
