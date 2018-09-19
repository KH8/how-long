package com.h8.howlong.admin.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.commands.CommandFactory;
import com.h8.howlong.admin.services.TimesheetManagementService;
import lombok.Getter;

@Getter
@Singleton
public class HowLongAdminContext {

    private final TimesheetManagementService timesheetManagementService;

    private final CommandFactory commandFactory;

    @Inject
    public HowLongAdminContext(
            TimesheetManagementService timesheetManagementService,
            CommandFactory commandFactory) {
        this.timesheetManagementService = timesheetManagementService;
        this.commandFactory = commandFactory;
    }
}