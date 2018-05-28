package com.h8.howlong.repositories;

import com.h8.howlong.configuration.ConfigurationProvider;
import com.h8.howlong.domain.TimesheetContext;

import javax.inject.Inject;

public class TimesheetContextRepository extends AbstractFileBasedRepository<TimesheetContext> {

    @Inject
    public TimesheetContextRepository(ConfigurationProvider configurationProvider) {
        super(configurationProvider.getProperty("db.file.name"));
    }

    @Override
    protected TimesheetContext initializeContent() {
        return new TimesheetContext();
    }

}
