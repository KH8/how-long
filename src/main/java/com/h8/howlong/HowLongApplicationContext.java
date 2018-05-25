package com.h8.howlong;

import com.h8.howlong.configuration.ConfigurationProvider;
import com.h8.howlong.printers.PrintingServiceFactory;
import com.h8.howlong.repositories.TimesheetContextRepository;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.TimesheetService;
import lombok.Getter;

@Getter
class HowLongApplicationContext {

    private final ConfigurationProvider configurationProvider;

    private final TimesheetContextRepository contextRepository;

    private final TimesheetContextService contextService;

    private final TimesheetService timesheetService;

    private final PrintingServiceFactory printingServiceFactory;

    HowLongApplicationContext() {
        configurationProvider = new ConfigurationProvider();
        contextRepository = new TimesheetContextRepository(configurationProvider.getProperty("db.file.name"));
        contextService = new TimesheetContextService(contextRepository);
        timesheetService = new TimesheetService(contextService);
        printingServiceFactory = new PrintingServiceFactory(contextService);
    }

    public PrintingServiceFactory getPrintingServiceFactory() {
        return printingServiceFactory;
    }
}