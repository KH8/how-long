package com.h8.howlong.domain;

import com.h8.howlong.utils.DayIndexCalculator;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class WorkTimestamp implements Serializable {

    private final LocalDateTime timestamp;

    public int getDayIndex() {
        return DayIndexCalculator.getDayIndex(timestamp);
    }

    public boolean isTimestampOfToday() {
        return LocalDateTime.now().getDayOfYear() == timestamp.getDayOfYear();
    }

}
