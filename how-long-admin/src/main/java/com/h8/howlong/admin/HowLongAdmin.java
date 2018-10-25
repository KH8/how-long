package com.h8.howlong.admin;

import com.google.inject.Guice;
import com.h8.howlong.admin.configuration.HowLongAdminContext;
import com.h8.howlong.admin.utils.ArgumentResolutionFailedException;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.utils.Logger;

public class HowLongAdmin {

    private static final String LS = System.lineSeparator();

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
            if (result.isSuccessful()) {
                printSuccess(result.getMessage());
            } else {
                printError(result.getMessage());
            }
        } catch (ArgumentResolutionFailedException e) {
            printError(e);
        }
    }

    private static void printSuccess(String message) {
        Logger.log("Command executed successfully:", message);
    }

    private static void printError(String message) {
        Logger.log("Command failed because of an error:", message);
        System.out.println(printUsage());
    }

    private static void printError(Exception e) {
        printError(e.getMessage());
        e.printStackTrace();
    }

    private static String printUsage() {
        return ""
                + "The following arguments are available:" + LS
                + LS
                + "- LIST --month={MONTH}" + LS
                + "  Lists all records for given month." + LS
                + "  Month argument is optional, if not provided current month is taken." + LS
                + LS
                + "- UPDATE --month={MONTH} --day={DAY} --start-time={hh:mm:ss} --end-time={hh:mm:ss}" + LS
                + "  Updates record of given month and day." + LS
                + "  If record does not exist new record is created." + LS
                + LS
                + "- DELETE --month={MONTH} --day={DAY}" + LS
                + "  Deletes record of given month and day.";
    }

}