package com.catstore.controller;

import com.catstore.feign.BillingServiceClient;
import com.catstore.model.BookPurchaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    BillingServiceClient billingServiceClient;

    @GetMapping("/calcBill")
    void calcBill() {
        List<BookPurchaseInfo> purchaseInfos = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            purchaseInfos.add(new BookPurchaseInfo(1, BigDecimal.valueOf(100)));
        }
        System.out.println("total price: " + billingServiceClient.calcBill(purchaseInfos));
    }
}
