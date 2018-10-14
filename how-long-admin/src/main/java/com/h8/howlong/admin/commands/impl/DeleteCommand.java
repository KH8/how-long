package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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
        try {
            timesheetManagementService.delete(month, day);
            return CommandResult.ok(
                    String.format("The day '%s'.'%s' has been deleted",
                            day, month));
        } catch (TimesheetManagementFailedException e) {
            return CommandResult.error(
                    String.format("The day '%s'.'%s' could not be deleted because of an exception: %s",
                            day, month, e.getMessage()));
        }
    }

}

