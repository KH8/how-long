package com.h8.howlong.admin;

import com.google.inject.Guice;
import com.h8.howlong.admin.configuration.HowLongAdminContext;
import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.utils.Logger;

public class HowLongAdmin {

    private static final HowLongAdminContext applicationContext;

    static {
        var injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongAdminContext.class);
    }

    public static void main(String[] args) {

        var arguments = new ArgumentResolver(args);
        var service = resolveAdministrationService(arguments);
        Logger.log(service.modifyTimesheet(args));
    }

    private static AdministrationService resolveAdministrationService(ArgumentResolver arguments) {
        var factory = applicationContext.getAdministrationServiceFactory();
        if (arguments.updateMode()) {
            return factory.getUpdateCommand();
        } else if (arguments.listMode()) {
            return factory.getListCommand();
        } else if (arguments.deleteMode()) {
            return factory.getDeleteCommand();
        } else {
            return factory.getUnknownCommand();
        }
    }

}