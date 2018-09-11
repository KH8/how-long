package com.h8.howlong.admin.services.impl;

import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;

public class UnknownCommandAdministrationService implements AdministrationService {

    @Override
    public String modifyTimesheet(ArgumentResolver argumentResolver) {
        return "Command unknown, nothing has been modified. Please refer to README file for more details";
    }
}
