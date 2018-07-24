package com.h8.howlong.repositories;

import com.google.inject.Singleton;
import com.h8.howlong.configuration.ConfigurationProvider;
import com.h8.howlong.domain.TimesheetContext;

import javax.inject.Inject;

@Singleton
public class TimesheetContextRepository extends AbstractFileBasedRepository<TimesheetContext> {

    private static final String DB_FILE_NAME_PROP = "db.file.name";

    @Inject
    public TimesheetContextRepository(ConfigurationProvider configurationProvider) {
        super(configurationProvider.getProperty(DB_FILE_NAME_PROP));
    }

    @Override
    protected TimesheetContext initializeContent() {
        return new TimesheetContext();
    }

}
