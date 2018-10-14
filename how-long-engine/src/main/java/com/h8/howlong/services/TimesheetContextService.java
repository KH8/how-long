package com.h8.howlong.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.domain.TimesheetContext;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.repositories.TimesheetContextRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class TimesheetContextService {

    private TimesheetContextRepository repository;

    private TimesheetContext context;

    private Map<LocalDate, WorkDay> timesheets;

    @Inject
    public TimesheetContextService(TimesheetContextRepository repository) {
        this.repository = repository;
        this.context = repository.readContent()
                .orElseThrow(() -> new IllegalStateException("Repository has not been initialized properly"));
        this.timesheets = context.getTimesheets();
    }

    public WorkDay getWorkDayOfToday() {
        var currentTimestamp = LocalDateTime.now();
        var key = currentTimestamp.toLocalDate();
        if (!timesheets.containsKey(key)) {
            timesheets.put(key, createWorkDayOfToday());
        }
        return timesheets.get(key);
    }

    public Optional<WorkDay> getWorkDayOf(int month, int day) {
        var currentTimestamp = LocalDateTime.now();
        var key = LocalDate.of(currentTimestamp.getYear(), month, day);
        return Optional.ofNullable(timesheets.get(key));
    }

    public WorkDay updateWorkDay(WorkDay wd) {
        var startTimestamp = wd.getStart();
        var key = startTimestamp.toLocalDate();
        timesheets.put(key, wd);
        repository.writeContent(context);
        return wd;
    }

    public Integer getTotalWorkingDayCount(int month) {
        return getTimesheetForMonth(month).size();
    }

    public Duration getTotalWorkingTime(int month) {
        return getTimesheetForMonth(month)
                .stream()
                .map(d -> Duration.between(d.getStart(), d.getEnd()))
                .reduce(Duration::plus)
                .orElse(Duration.ZERO);
    }

    public Duration getAverageWorkingTime(int month) {
        var total = getTotalWorkingTime(month);
        var count = getTimesheetForMonth(month).size();
        return Duration.ofMillis(total.toMillis() / Math.max(count, 1));
    }

    public List<WorkDay> getTimesheetForMonth(int month) {
        var today = LocalDate.now();
        return timesheets.values()
                .stream()
                .filter(d -> today.getYear() == d.getStart().getYear())
                .filter(d -> d.getStart().getMonthValue() == month)
                .sorted(Comparator.comparing(WorkDay::getStart))
                .collect(Collectors.toList());
    }

    public boolean deleteWorkday(int month, int day) {
        var currentTimestamp = LocalDateTime.now();
        var key = LocalDate.of(currentTimestamp.getYear(), month, day);
        var deleted = timesheets.remove(key) != null;
        if (deleted) {
            repository.writeContent(context);
        }
        return deleted;
    }

    public WorkDay createWorkDayOfGivenDate(LocalDateTime start, LocalDateTime end) {
        return WorkDay.builder()
                .start(start)
                .end(end)
                .build();
    }

    private WorkDay createWorkDayOfToday() {
        return WorkDay.builder()
                .start(LocalDateTime.now())
                .build();
    }

}
