package com.h8.howlong;

import com.h8.howlong.services.StartWorkTimestampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HowLongApplication implements CommandLineRunner {

    private StartWorkTimestampService service;

    @Autowired
    public HowLongApplication(StartWorkTimestampService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(HowLongApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LocalDateTime startTime = service.initTimestamp().getTimestamp();
        System.out.println("Today is " + startTime.toLocalDate());
        System.out.println("- started at: " + startTime.toLocalTime());
        System.out.println("- elapsed time: " + service.getElapsedTime());
        System.out.println("- remaining time: " + service.getRemainingTime());
        System.out.println("Enjoy the day!");
    }

}