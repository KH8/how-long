package com.h8.howlong.admin.commands;

import com.h8.howlong.admin.services.TimesheetManagementService;

import javax.inject.Inject;

public abstract class AbstractManagementCommand implements Command {

    protected TimesheetManagementService timesheetManagementService;

    @Inject
    public AbstractManagementCommand(TimesheetManagementService timesheetManagementService) {
        this.timesheetManagementService = timesheetManagementService;
    }

    public abstract CommandResult execute();
    
}
