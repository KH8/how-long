package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementService;

public class DeleteCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    public DeleteCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
    }

    @Override
    public CommandResult execute() {
        //TODO!!!
        return null;
    }

}
