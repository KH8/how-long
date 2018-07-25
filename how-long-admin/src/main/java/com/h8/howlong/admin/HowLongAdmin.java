package com.h8.howlong.admin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.h8.howlong.admin.configuration.HowLongAdminContext;
import com.h8.howlong.utils.Logger;

public class HowLongAdmin {

    private static final HowLongAdminContext applicationContext;

    static {
        Injector injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongAdminContext.class);
    }

    public static void main(String[] args) {
        Logger.log(applicationContext.getListCommand().list(7));
    }

}