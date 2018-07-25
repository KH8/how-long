package com.h8.howlong.app.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.app.services.impl.CalendarPrintingService;
import com.h8.howlong.app.services.impl.DefaultPrintingService;
import com.h8.howlong.app.services.impl.ListPrintingService;
import com.h8.howlong.app.services.impl.QuietPrintingService;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.WorkDayComputationService;

@Singleton
public class PrintingServiceFactory {

    private final TimesheetContextService contextService;

    private final WorkDayComputationService computationService;

    @Inject
    public PrintingServiceFactory(
            TimesheetContextService contextService,
            WorkDayComputationService computationService) {
        this.contextService = contextService;
        this.computationService = computationService;
    }

    public PrintingService getQuietPrinter() {
        return new QuietPrintingService(contextService, computationService);
    }

    public PrintingService getDefaultPrinter() {
        return new DefaultPrintingService(contextService, computationService);
    }

    public PrintingService getCalendarPrinter() {
        return new CalendarPrintingService(contextService);
    }

    public PrintingService getListPrinter() {
        return new ListPrintingService(contextService);
    }

}
