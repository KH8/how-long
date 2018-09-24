package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.DateFormatSymbols;
import java.util.Locale;

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
        String monthName = new DateFormatSymbols(Locale.ENGLISH).getMonths()[month - 1];
        try {
            timesheetManagementService.delete(month, day);
        } catch (TimesheetManagementFailedException e) {
            return CommandResult.error(String.format("The day '%s' of '%s' has not been found and therefore, has not been deleted", day, monthName));
        }
        return CommandResult.ok(String.format("The day '%s' of '%s' has been deleted", day, monthName));
    }

}

