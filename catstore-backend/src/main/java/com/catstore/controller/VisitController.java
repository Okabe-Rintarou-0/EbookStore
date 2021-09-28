package com.catstore.controller;

import com.catstore.annotation.SkipSessionCheck;
import com.catstore.statistic.GlobalStatistic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
public class VisitController {
    @GetMapping
    //For jmeter test. Otherwise will need login to pass interceptor.
    //Method with this annotation will pass session check directly and unconditionally.
    @SkipSessionCheck
    long getVisit() {
        return GlobalStatistic.addVisit();
    }
}
