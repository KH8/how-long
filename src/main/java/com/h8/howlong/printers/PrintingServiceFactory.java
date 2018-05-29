package com.h8.howlong.printers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.services.TimesheetContextService;

@Singleton
public class PrintingServiceFactory {

    private final TimesheetContextService contextService;

    @Inject
    public PrintingServiceFactory(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    public PrintingService getDefaultPrinter() {
        return new DefaultPrintingService(contextService);
    }

    public PrintingService getCalendarPrinter() {
        return new CalendarPrintingService(contextService);
    }

    public PrintingService getListPrinter() {
        return new ListPrintingService(contextService);
    }

}
