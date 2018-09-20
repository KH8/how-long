package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.*;
import com.h8.howlong.admin.services.*;
import lombok.*;

import java.time.*;

@Data
@EqualsAndHashCode(callSuper = true)

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
        var result = CommandResultStatus.SUCCESS;
        try {
            timesheetManagementService.updateStartTime(month, day, start);
            timesheetManagementService.updateEndTime(month, day, start);
        } catch (TimesheetManagementFailedException e) {
            result = CommandResultStatus.ERROR;
        }
        return CommandResult.builder()
                .status(CommandResultStatus.SUCCESS)
                .message(result.toString())
                .build();
    }

}
