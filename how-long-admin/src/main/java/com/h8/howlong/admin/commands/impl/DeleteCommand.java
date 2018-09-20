package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.*;
import com.h8.howlong.admin.services.*;
import lombok.*;

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
        var result = CommandResultStatus.SUCCESS;
        try {
            timesheetManagementService.delete(month, day);
        } catch (TimesheetManagementFailedException e) {
            result = CommandResultStatus.ERROR;
        }
        return CommandResult.builder()
                .status(result)
                .message(result.toString())
                .build();
    }

}

