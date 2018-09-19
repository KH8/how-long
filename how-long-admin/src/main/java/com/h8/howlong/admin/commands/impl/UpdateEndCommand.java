package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEndCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    private final LocalDateTime end;

    public UpdateEndCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day, LocalDateTime end) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
        this.end = end;
    }

    @Override
    public CommandResult execute() {
        //TODO!!!
        return null;
    }

}
