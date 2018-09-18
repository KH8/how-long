package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementService;

import java.time.LocalDateTime;

public class UpdateFullCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    private final LocalDateTime start;

    private final LocalDateTime end;

    public UpdateFullCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day,
            LocalDateTime start, LocalDateTime end) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute() {
        //TODO!!!
        return null;
    }

}
