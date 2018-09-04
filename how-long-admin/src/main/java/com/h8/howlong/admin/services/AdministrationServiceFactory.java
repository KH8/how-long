package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.impl.DeleteCommandAdministrationService;
import com.h8.howlong.admin.services.impl.ListCommandAdministrationService;
import com.h8.howlong.admin.services.impl.UnknownCommandAdministrationService;
import com.h8.howlong.admin.services.impl.UpdateCommandAdministrationService;
import com.h8.howlong.services.TimesheetContextService;

@Singleton
public class AdministrationServiceFactory {

    private final TimesheetContextService contextService;

    @Inject
    public AdministrationServiceFactory(
            TimesheetContextService contextService) {
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
