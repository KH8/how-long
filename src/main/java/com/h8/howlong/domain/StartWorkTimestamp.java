package com.h8.howlong.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class StartWorkTimestamp implements Serializable {

    private final LocalDateTime timestamp;

    public boolean isTimestampOfToday() {
        return LocalDateTime.now().getDayOfYear() == timestamp.getDayOfYear();
    }

}
