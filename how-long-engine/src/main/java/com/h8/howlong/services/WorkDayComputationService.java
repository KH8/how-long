package com.h8.howlong.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.configuration.ConfigurationProvider;
import com.h8.howlong.domain.WorkDay;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class WorkDayComputationService {

    private static final String WORKDAY_DURATION_PROP = "workDay.duration";

    private final ConfigurationProvider configurationProvider;

    private final TimesheetContextService contextService;

    @Inject
    public WorkDayComputationService(
            ConfigurationProvider configurationProvider,
            TimesheetContextService contextService) {
        this.configurationProvider = configurationProvider;
        this.contextService = contextService;
    }

    public Duration getElapsedTime() {
        WorkDay wd = contextService.getWorkDayOfToday();
        return getElapsedTime(wd);
    }

    public Duration getRemainingTime() {
        return Duration.between(LocalDateTime.now(), getSuggestedEndTime());
    }

    public LocalDateTime getSuggestedEndTime() {
        WorkDay wd = contextService.getWorkDayOfToday();
        List<WorkDay> timesheet = contextService.getTimesheetForMonth(wd.getStart().getMonthValue());
        Integer n = timesheet.size();

        timesheet.remove(wd);
        Long totalSeconds = timesheet
                .stream()
                .map(this::getElapsedTime)
                .reduce(Duration.ZERO, Duration::plus)
                .toMillis() / 1000;

        Long suggestedWorkDurationSeconds = getWorkDayDurationSeconds() * n - totalSeconds;
        return wd.getStart().plusSeconds(suggestedWorkDurationSeconds);
    }

    private Duration getElapsedTime(WorkDay wd) {
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

    private Integer getWorkDayDurationSeconds() {
        Double d = Double.parseDouble(configurationProvider.getProperty(WORKDAY_DURATION_PROP)) * 3600;
        return d.intValue();
    }

}
