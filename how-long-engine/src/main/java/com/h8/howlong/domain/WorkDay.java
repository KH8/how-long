package com.h8.howlong.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Setter
public class WorkDay implements Serializable {

    private LocalDateTime start;

    private LocalDateTime end;

}
