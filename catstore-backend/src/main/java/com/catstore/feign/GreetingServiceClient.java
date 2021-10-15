//package com.catstore.feign;
//
//import org.springframework.cloud.openfeign.FeignAutoConfiguration;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Component
//@FeignClient(value = "serverless", configuration = FeignAutoConfiguration.class)
//public interface GreetingServiceClient {
//    @PostMapping("/greeting")
//    String greet(@RequestBody String name);
//}
