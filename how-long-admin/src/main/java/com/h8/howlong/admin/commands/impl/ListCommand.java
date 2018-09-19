package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.AbstractManagementCommand;
import com.h8.howlong.admin.commands.CommandResult;
import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.services.TimesheetManagementService;
import com.h8.howlong.utils.DurationUtils;
import com.h8.howlong.utils.print.PrintBuilder;
import lombok.Data;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Data
public class ListCommand extends AbstractManagementCommand {

    private final Integer month;

    public ListCommand(TimesheetManagementService timesheetManagementService, Integer month) {
        super(timesheetManagementService);
        this.month = month;
    }

    @Override
    public CommandResult execute() {
        var timesheet = timesheetManagementService.getTimesheet(month);
        var b = PrintBuilder.builder();
        b.ln(String.format("Timesheet for: <c2018/%02d>", month));
        timesheet.forEach(w ->
                b.ln(String.format("<c%02d> | %s | %s | %s",
                        w.getStart().getDayOfMonth(),
                        w.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        w.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        DurationUtils.format(Duration.between(w.getStart(), w.getEnd())))
                )
        );
        return CommandResult.builder()
                .message(b.build())
                .status(CommandResultStatus.SUCCESS)
                .build();
    }

}
