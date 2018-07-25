package com.h8.howlong.admin.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.services.TimesheetService;
import lombok.Getter;

@Getter
@Singleton
public class HowLongAdminContext {

    private final TimesheetService timesheetService;

    @Inject
    public HowLongAdminContext(
            TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

}