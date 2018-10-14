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
public class UpdateEndCommand extends AbstractManagementCommand {

    private final Integer month;

    private final Integer day;

    private final LocalTime end;

    public UpdateEndCommand(
            TimesheetManagementService timesheetManagementService, Integer month, Integer day, LocalTime end) {
        super(timesheetManagementService);
        this.month = month;
        this.day = day;
        this.end = end;
    }

    @Override
    public CommandResult execute() {
        try {
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
