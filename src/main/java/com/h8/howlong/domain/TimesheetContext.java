package com.h8.howlong.domain;

import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class TimesheetContext implements Serializable {

    @Singular
    private final Map<Integer, WorkDay> timesheets;

    public TimesheetContext() {
        this.timesheets = new HashMap<>();
    }

}
