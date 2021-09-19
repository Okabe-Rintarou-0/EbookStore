package com.catstore.listener;

import com.catstore.model.OrderInfo;
import com.catstore.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author lzh
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/18 14:53
 */
@Component
public class OrderMessageListener {
    @Autowired
    private UserOrderService userOrderService;

    @JmsListener(destination = "order")
    public void handleOrderMessage(OrderInfo orderInfo){
        System.out.println("Listener received :" + orderInfo.toString());
        userOrderService.placeOrder(orderInfo);
    }
}
