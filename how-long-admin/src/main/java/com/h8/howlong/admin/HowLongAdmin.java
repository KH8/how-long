package com.h8.howlong.admin;

import com.google.inject.Guice;
import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.configuration.HowLongAdminContext;
import com.h8.howlong.admin.utils.ArgumentResolutionFailedException;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.utils.Logger;

public class HowLongAdmin {

    private static final HowLongAdminContext applicationContext;

    static {
        var injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongAdminContext.class);
    }

    public static void main(String[] args) {
        var arguments = new ArgumentResolver(args);
        try {
            var command = applicationContext.getCommandFactory().resolveCommand(arguments);
            var result = command.execute();
            if (CommandResultStatus.SUCCESS.equals(result.getStatus())) {
                Logger.log(result.getMessage());
            } else {
                Logger.log("Command failed because of an error:");
                Logger.log(result.getMessage());
                Logger.log(printUsage());
            }
        } catch (ArgumentResolutionFailedException e) {
            e.printStackTrace();
            System.out.print(printUsage());
        }
    }

    private static String printUsage() {
        return "The following arguments are available:\n" +
                "\n" +
                "- `LIST` --month={MONTH}\n" +
                "\n" +
                "    Lists all records for given month.\n" +
                "    Month argument is optional, if not provided current month is taken.\n" +
                "\n" +
                "- `UPDATE` --month={MONTH} --day={DAY} --start-time={hh:mm:ss} --end-time={hh:mm:ss}\n" +
                "\n" +
                "    Updates record of given month and day.\n" +
                "    If record does not exist new record is created.\n" +
                "\n" +
                "- `DELETE` --month={MONTH} --day={DAY}\n" +
                "\n" +
                "    Deletes record of given month and day.";
    }

}