package com.h8.howlong.printers;

import com.h8.howlong.services.TimesheetContextService;

public class PrintingServiceFactory {

    private final TimesheetContextService contextService;

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
