package com.h8.howlong.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class WorkDay implements Serializable {

    private final LocalDateTime start;

    private LocalDateTime end;

}
