package com.catstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling //开启定时任务
@SpringBootApplication
public class CatStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatStoreApplication.class, args);
    }
}
