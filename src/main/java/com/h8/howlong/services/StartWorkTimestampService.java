package com.h8.howlong.services;

import com.h8.howlong.domain.StartWorkTimestamp;
import com.h8.howlong.repositories.StartWorkTimestampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StartWorkTimestampService {

    private StartWorkTimestampRepository repository;

    @Autowired
    public StartWorkTimestampService(StartWorkTimestampRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public StartWorkTimestamp initTimestamp() {
        StartWorkTimestamp top = findTimestampOfToday();
        return isTimestampOfToday(top) ? top : createTimestampOfToday();
    }

    @Transactional
    public Duration getElapsedTime() {
        StartWorkTimestamp top = findTimestampOfToday();
        return Duration.between(top.getTimestamp(), LocalDateTime.now());
    }

    @Transactional
    public Duration getRemainingTime() {
        Duration elapsedTime = getElapsedTime();
        return Duration.ofHours(8).minus(elapsedTime);
    }

    private StartWorkTimestamp createTimestampOfToday() {
        StartWorkTimestamp now = StartWorkTimestamp.builder()
                .timestamp(LocalDateTime.now())
                .build();
        return repository.save(now);
    }

    private Boolean isTimestampOfToday(StartWorkTimestamp latest) {
        return latest.getTimestamp().getDayOfYear() == LocalDateTime.now().getDayOfYear();
    }

    private StartWorkTimestamp findTimestampOfToday() {
        return repository.findTopByTimestampAfter(LocalDate.now().atStartOfDay())
                .orElseGet(this::createTimestampOfToday);
    }

}
