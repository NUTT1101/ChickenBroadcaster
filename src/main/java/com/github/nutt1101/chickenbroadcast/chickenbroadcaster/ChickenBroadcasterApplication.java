package com.github.nutt1101.chickenbroadcast.chickenbroadcaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChickenBroadcasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChickenBroadcasterApplication.class, args);
    }
}
