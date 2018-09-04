package com.h8.howlong.admin.services.impl;

import com.google.inject.Inject;
import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.services.TimesheetContextService;

public class DeleteCommandAdministrationService implements AdministrationService {

    private final TimesheetContextService service;

    @Inject
    public DeleteCommandAdministrationService(TimesheetContextService service) {
        this.service = service;
    }

    @Override
    public String modifyTimesheet(ArgumentResolver ar) {
        return deleteDay(ar.getMonth(), ar.getDay());
    }

    private String deleteDay(int month, int day) {
        if (service.deleteWorkday(month, day)) {
            return "The provided day has been deleted";
        }
        return "The provided day has not been found";
    }
}
