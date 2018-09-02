package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.WorkDayComputationService;

@Singleton
public class AdministrationServiceFactory {

    private final TimesheetContextService contextService;

    @Inject
    public AdministrationServiceFactory(
            TimesheetContextService contextService,
            WorkDayComputationService computationService) {
        this.contextService = contextService;
    }

    public AdministrationService getUpdateCommand() {
        return new UpdateCommandAdministrationService(contextService);
    }

    public AdministrationService getListCommand() {
        return new ListCommandAdministrationService(contextService);
    }

    public AdministrationService getDeleteCommand() {
        return new DeleteCommandAdministrationService(contextService);
    }

    public AdministrationService getUnknownCommand() {
        return new UnknownCommandAdministrationService();
    }
}
