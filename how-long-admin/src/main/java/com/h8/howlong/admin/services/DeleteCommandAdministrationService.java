package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.h8.howlong.services.TimesheetContextService;

public class DeleteCommandAdministrationService extends AdministrationService {

    private final TimesheetContextService service;

    @Inject
    public DeleteCommandAdministrationService(TimesheetContextService service) {
        this.service = service;
    }

    @Override
    public String modifyTimesheet(String[] args) {
        return deleteDay(args);
    }

    private String deleteDay(String[] args) {
        int month = Integer.parseInt(args[1]);
        int day = Integer.parseInt(args[2]);
        var message = "The provided day has not been found";
        if (service.deleteWorkday(month, day)) {
            message = "The provided day has been deleted";
        }
        return message;
    }
}
