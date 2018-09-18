package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementService;

import java.time.LocalDateTime;

public class UpdateStartCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    private final LocalDateTime start;

    public UpdateStartCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day, LocalDateTime start) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
        this.start = start;
    }

    @Override
    public CommandResult execute() {
        //TODO!!!
        return null;
    }

}
