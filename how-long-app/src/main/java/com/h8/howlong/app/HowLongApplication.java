package com.h8.howlong.app;

import com.google.inject.*;
import com.h8.howlong.app.configuration.*;
import com.h8.howlong.app.services.*;
import com.h8.howlong.app.utils.*;
import com.h8.howlong.utils.*;

public class HowLongApplication {

    private static final HowLongApplicationContext applicationContext;

    static {
        var injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongApplicationContext.class);
    }

    public static void main(String[] args) {
        applicationContext.getTimesheetService().updateWorkDayEndTime();
        var arguments = new ArgumentResolver(args);
        var service = resolvePrinter(arguments);
        Logger.log(service.print(arguments.calendarMonth()));
    }

    private static PrintingService resolvePrinter(ArgumentResolver arguments) {
        var factory = applicationContext.getPrintingServiceFactory();
        if (arguments.quietMode()) {
            return factory.getQuietPrinter();
        } else if (arguments.calendarMode()) {
            return factory.getCalendarPrinter();
        } else if (arguments.listMode()) {
            return factory.getListPrinter();
        } else {
            return factory.getDefaultPrinter();
        }
    }

}