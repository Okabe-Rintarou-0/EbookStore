package com.catstore.serverless;

import com.catstore.serverless.model.BookPurchaseInfo;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.function.Function;

@SpringBootApplication
@EnableEurekaClient
public class ServerlessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerlessApplication.class, args);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> greet() {
        return flux -> flux.map(name -> "Hello, " + name + "!");
    }

    @Bean
    public Function<Flux<BookPurchaseInfo>, Publisher<BigDecimal>> calcBill() {
        return flux -> flux
                .map(info -> info.price.multiply(BigDecimal.valueOf(info.purchaseNumber)))
                .reduce(BigDecimal::add);
    }
}
