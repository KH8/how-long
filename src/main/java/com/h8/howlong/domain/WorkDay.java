package com.h8.howlong.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WorkDay implements Serializable {

    private final WorkTimestamp start;

    private WorkTimestamp end;

}
