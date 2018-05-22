package com.h8.howlong.services;

import com.h8.howlong.domain.TimesheetContext;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.repositories.TimesheetContextRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class TimesheetContextService {

    private TimesheetContextRepository repository;

    private TimesheetContext context;

    private Map<LocalDate, WorkDay> timesheets;

    public TimesheetContextService() {
        this.repository = new TimesheetContextRepository();
        this.context = repository.readContent()
                .orElseThrow(() -> new IllegalStateException("Repository has not been initialized properly"));
        this.timesheets = context.getTimesheets();
    }

    public WorkDay getWorkDayOfToday() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        LocalDate key = currentTimestamp.toLocalDate();
        if (!timesheets.containsKey(key)) {
            timesheets.put(key, createWorkDayOfToday());
        }
        return timesheets.get(key);
    }

    public WorkDay updateWorkDay(WorkDay wd) {
        LocalDateTime startTimestamp = wd.getStart();
        LocalDate key = startTimestamp.toLocalDate();
        timesheets.put(key, wd);
        repository.writeContent(context);
        return wd;
    }

    public Duration getTotalWorkingTime(int month) {
        LocalDate today = LocalDate.now();
        return timesheets.values()
                .stream()
                .filter(d -> today.getYear() == d.getStart().getYear())
                .filter(d -> today.getMonthValue() == month)
                .map(d -> Duration.between(d.getStart(), d.getEnd()))
                .reduce(Duration::plus)
                .orElse(Duration.ZERO);
    }

    public Map<LocalDate, WorkDay> getTimesheets() {
        return timesheets;
    }

    private WorkDay createWorkDayOfToday() {
        return WorkDay.builder()
                .start(LocalDateTime.now())
                .build();
    }

}
