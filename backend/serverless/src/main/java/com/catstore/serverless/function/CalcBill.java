//package com.catstore.serverless.function;
//
//import com.catstore.serverless.model.BookPurchaseInfo;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//
//import java.math.BigDecimal;
//import java.util.function.Function;
//
//@Component
//public class CalcBill implements Function<Flux<BookPurchaseInfo>, BigDecimal> {
//    @Override
//    public BigDecimal apply(Flux<BookPurchaseInfo> flux) {
//        return flux
//                .map(info -> info.price.multiply(BigDecimal.valueOf(info.purchaseNumber)))
//                .reduce(BigDecimal::add)
//                .block();
//    }
//}
//
