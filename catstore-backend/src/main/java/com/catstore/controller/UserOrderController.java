package com.catstore.controller;

import com.catstore.entity.UserOrder;
import com.catstore.model.Message;
import com.catstore.model.OrderInfo;
import com.catstore.service.UserOrderService;
import com.catstore.service.UserService;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@RestController
//Users will frequently visit this controller. So assign a controller for each session.
@Scope(scopeName = "session")
@RequestMapping("/order")
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private UserService userService;

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @PostMapping("/user/place")
    Message placeOrder(@RequestBody OrderInfo orderInfo) {
        System.out.println("Place order" + orderInfo.toString());
        Integer userId = SessionUtil.getUserId();
        orderInfo.userId = userId;
        orderInfo.userProperty = userService.getUserProperty(userId);
        //Use jms.
        jmsMessagingTemplate.convertAndSend("order", orderInfo);
        return MessageUtil.createMessage(MessageUtil.STAT_OK, MessageUtil.PLACE_ORDER_SUCCESS_MSG);
    }

    @GetMapping("/user/all")
    ArrayList<UserOrder> getAllOrders() {
        return userOrderService.getAllOrders();
    }

    @GetMapping("/manager/all")
    ArrayList<UserOrder> getAllOrdersForManager() {
        return userOrderService.getAllOrdersForManager();
    }

    @PostMapping("/manager/search")
    ArrayList<UserOrder> searchOrdersForManager(@RequestBody ArrayList<Date> startNEndDates) {
        System.out.println(startNEndDates.toString());
        if (startNEndDates.size() == 2)
            return userOrderService.getOrdersInRangeForManager(startNEndDates.get(0), startNEndDates.get(1));
        return null;
    }

    @PostMapping("/user/search")
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
