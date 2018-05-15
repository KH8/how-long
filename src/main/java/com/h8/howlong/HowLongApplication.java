package com.h8.howlong;

import com.h8.howlong.services.StartWorkTimestampService;
import com.h8.howlong.utils.DurationUtils;

import java.time.LocalDateTime;

public class HowLongApplication {

    private static final StartWorkTimestampService service = new StartWorkTimestampService();

    public static void main(String[] args) {
        LocalDateTime startTime = service.initTimestamp().getTimestamp();
        System.out.println("Today is " + startTime.toLocalDate());
        System.out.println("- started at: " + startTime.toLocalTime());
        System.out.println("- elapsed time: " + DurationUtils.format(service.getElapsedTime()));
        System.out.println("- remaining time: " + DurationUtils.format(service.getRemainingTime()));
        System.out.println("Enjoy the day!");
    }

}