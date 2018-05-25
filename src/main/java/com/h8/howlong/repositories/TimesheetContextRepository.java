package com.h8.howlong.repositories;

import com.h8.howlong.domain.TimesheetContext;

public class TimesheetContextRepository extends AbstractFileBasedRepository<TimesheetContext> {

    public TimesheetContextRepository(String dbFileName) {
        super(dbFileName);
    }

    @Override
    protected TimesheetContext initializeContent() {
        return new TimesheetContext();
    }

}
