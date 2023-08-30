package com.catstore.listener;

import com.catstore.model.OrderInfo;
import com.catstore.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {
    @Autowired
    private UserOrderService userOrderService;

    //Listen to the message coming from topic "order"
    @JmsListener(destination = "order")
    public void handleOrderMessage(OrderInfo orderInfo) {
        System.out.println("Listener received :" + orderInfo.toString());
        //place order.
        try {
            userOrderService.placeOrder(orderInfo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
