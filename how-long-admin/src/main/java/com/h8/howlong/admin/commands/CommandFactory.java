package com.h8.howlong.admin.commands;

import com.google.inject.Singleton;
import com.h8.howlong.admin.commands.impl.*;
import com.h8.howlong.admin.services.TimesheetManagementService;
import com.h8.howlong.admin.utils.ArgumentResolutionFailedException;
import com.h8.howlong.admin.utils.ArgumentResolver;

import javax.inject.Inject;

@Singleton
public class CommandFactory {

    private TimesheetManagementService timesheetManagementService;

    @Inject
    public CommandFactory(TimesheetManagementService timesheetManagementService) {
        this.timesheetManagementService = timesheetManagementService;
    }

    public Command resolveCommand(ArgumentResolver args)
            throws ArgumentResolutionFailedException {
        var command = args.getCommand();
        switch (command) {
            case LIST:
                return resolveListCommand(args);
            case UPDATE:
                return resolveUpdateCommand(args);
            case DELETE:
                return resolveDeleteCommand(args);
        }
        throw new ArgumentResolutionFailedException("Could not resolve command");
    }

    private Command resolveListCommand(ArgumentResolver args)
            throws ArgumentResolutionFailedException {
        return new ListCommand(
                timesheetManagementService,
                args.getMonth());
    }

    private Command resolveUpdateCommand(ArgumentResolver args)
            throws ArgumentResolutionFailedException {
        var mode = args.getUpdateMode();
        switch (mode) {
            case START:
                return new UpdateStartCommand(
                        timesheetManagementService,
                        args.getMonth(), args.getDay(), args.getStartTime());
            case END:
                return new UpdateEndCommand(
                        timesheetManagementService,
                        args.getMonth(), args.getDay(), args.getEndTime());
            case FULL:
                return new UpdateFullCommand(
                        timesheetManagementService,
                        args.getMonth(), args.getDay(), args.getStartTime(), args.getEndTime());
        }
        throw new ArgumentResolutionFailedException("Could not update mode");
    }

    private Command resolveDeleteCommand(ArgumentResolver args)
            throws ArgumentResolutionFailedException {
        return new DeleteCommand(timesheetManagementService,
                args.getMonth(), args.getDay());
    }

}
