package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.*;
import com.h8.howlong.admin.services.*;
import lombok.*;

import java.time.*;

@Data
@EqualsAndHashCode(callSuper = true)

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
        var result = CommandResultStatus.SUCCESS;
        try {
            timesheetManagementService.updateStartTime(month, day, start);
        } catch (TimesheetManagementFailedException e) {
            result = CommandResultStatus.ERROR;
        }
        return CommandResult.builder()
                .status(CommandResultStatus.SUCCESS)
                .message(result.toString())
                .build();
    }

}
