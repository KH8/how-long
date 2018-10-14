package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateFullCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    private final LocalTime start;

    private final LocalTime end;

    public UpdateFullCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day,
            LocalTime start, LocalTime end) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    @Override
    public CommandResult execute() {
        try {
            timesheetManagementService.updateStartTime(month, day, start);
            timesheetManagementService.updateEndTime(month, day, end);
            return CommandResult.ok(
                    String.format("The day '%s'.'%s' has been updated",
                            day, month));
        } catch (TimesheetManagementFailedException e) {
            return CommandResult.error(
                    String.format("The day '%s'.'%s' could not be updated because of an exception: %s",
                            day, month, e.getMessage()));
        }
    }

}
