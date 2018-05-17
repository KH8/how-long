package com.h8.howlong.services;

import com.h8.howlong.domain.TimesheetContext;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.domain.WorkTimestamp;
import com.h8.howlong.repositories.TimesheetContextRepository;
import com.h8.howlong.utils.DayIndexCalculator;

import java.time.LocalDateTime;
import java.util.Map;

public class TimesheetContextService {

    private TimesheetContextRepository repository;

    private TimesheetContext context;

    private Map<Integer, WorkDay> timesheets;

    public TimesheetContextService() {
        this.repository = new TimesheetContextRepository();
        this.context = repository.readContent()
                .orElseThrow(() -> new IllegalStateException("Repository has not been initialized properly"));
        this.timesheets = context.getTimesheets();
    }

    public WorkDay getWorkDayOfToday() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        int index = DayIndexCalculator.getDayIndex(currentTimestamp);
        if (!timesheets.containsKey(index)) {
            timesheets.put(index, createWorkDayOfToday());
        }
        return timesheets.get(index);
    }

    public WorkDay updateWorkDay(WorkDay wd) {
        int index = DayIndexCalculator.getDayIndex(wd.getStart().getTimestamp());
        timesheets.put(index, wd);
        repository.writeContent(context);
        return wd;
    }

    private WorkDay createWorkDayOfToday() {
        WorkTimestamp timestamp = WorkTimestamp.builder()
                .timestamp(LocalDateTime.now())
                .build();
        return WorkDay.builder()
                .start(timestamp)
                .build();
    }

}
