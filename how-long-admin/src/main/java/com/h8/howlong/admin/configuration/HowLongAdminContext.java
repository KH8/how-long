package com.h8.howlong.admin.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.AdministrationServiceFactory;
import com.h8.howlong.admin.services.impl.ListCommandAdministrationService;
import lombok.Getter;

@Getter
@Singleton
public class HowLongAdminContext {

    private final ListCommandAdministrationService listCommandAdministrationService;

    private final AdministrationServiceFactory administrationServiceFactory;

    @Inject
    public HowLongAdminContext(
            ListCommandAdministrationService listCommandAdministrationService, AdministrationServiceFactory administrationServiceFactory) {
        this.listCommandAdministrationService = listCommandAdministrationService;
        this.administrationServiceFactory = administrationServiceFactory;
    }

}