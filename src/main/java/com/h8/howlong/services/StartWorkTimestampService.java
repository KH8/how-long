package com.h8.howlong.services;

import com.h8.howlong.domain.StartWorkTimestamp;
import com.h8.howlong.repositories.StartWorkTimestampRepository;

import java.time.Duration;
import java.time.LocalDateTime;

public class StartWorkTimestampService {

    private StartWorkTimestampRepository repository;

    public StartWorkTimestampService() {
        this.repository = new StartWorkTimestampRepository();
    }

    public StartWorkTimestamp initTimestamp() {
        StartWorkTimestamp latest = getLatestTimestamp();
        return latest.isTimestampOfToday() ? latest : createTimestampOfToday();
    }

    public Duration getElapsedTime() {
        StartWorkTimestamp latest = getLatestTimestamp();
        return Duration.between(latest.getTimestamp(), LocalDateTime.now());
    }

    public Duration getRemainingTime() {
        return Duration.ofHours(8).minus(getElapsedTime());
    }

    private StartWorkTimestamp getLatestTimestamp() {
        return repository.findLatest()
                .orElseGet(this::createTimestampOfToday);
    }

    private StartWorkTimestamp createTimestampOfToday() {
        StartWorkTimestamp now = StartWorkTimestamp.builder()
                .timestamp(LocalDateTime.now())
                .build();
        return repository.save(now)
                .orElseThrow(() -> new IllegalStateException("Timestamp could not be initialized properly"));
    }

}
