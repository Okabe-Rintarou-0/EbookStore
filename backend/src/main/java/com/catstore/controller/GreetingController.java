//package com.catstore.controller;
//
//import com.catstore.annotation.SkipSessionCheck;
//import com.catstore.feign.GreetingServiceClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class GreetingController {
//    @Autowired
//    GreetingServiceClient greetingServiceClient;
//
//    @GetMapping("/greet/{name}")
//    @SkipSessionCheck
//    String greet(@PathVariable(name = "name") String name) {
//        return greetingServiceClient.greet(name);
//    }
//}
