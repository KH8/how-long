package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.services.TimesheetContextService;

@Singleton
public class UpdateCommandAdministrationService extends AdministrationService {

    private final TimesheetContextService service;

    @Inject
    public UpdateCommandAdministrationService(TimesheetContextService service) {
        this.service = service;
    }

    @Override
    public String modifyTimesheet(String[] args) {
        return update(getUpdateMode(args[2]));
    }

    private String update(UpdateMode updateMode) {
        if (updateMode.equals(UpdateMode.FULL)) {
            return fullUpdate();
        } else return "Update mode unknown";
    }

    private String fullUpdate() {
//        int month = Integer.parseInt(args [1]);
//        int day = Integer.parseInt(args [2]);
//        UpdateMode mode = getUpdateMode(args[3]);
////        LocalTime times
//
//        var workDay = service.getWorkDayOf(month, day);
        return "done";
    }

    private UpdateMode getUpdateMode(String arg) {

        if ("FULL".equals(arg)) {
            return UpdateMode.FULL;
        } else if ("START_DATE".equals(arg)) {
            return UpdateMode.START_DATE;
        } else if ("END_DATE".equals(arg)) {
            return UpdateMode.END_DATE;
        } else
            throw new IllegalArgumentException("FULL, START_DATE or END_DATE command expected");
    }


    public enum UpdateMode {
        FULL,
        START_DATE,
        END_DATE
    }

}
