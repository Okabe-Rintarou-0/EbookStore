package com.catstore.feign;

import com.catstore.model.BookPurchaseInfo;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Component
@FeignClient(value = "serverless", configuration = FeignAutoConfiguration.class)
public interface BillingServiceClient {
    @PostMapping("/calcBill")
    List<BigDecimal> calcBill(@RequestBody List<BookPurchaseInfo> bookPurchaseInfos);
}
