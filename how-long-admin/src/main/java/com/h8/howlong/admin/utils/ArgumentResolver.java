package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommands;

public class ArgumentResolver {

    private final String[] args;

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public Boolean listMode() {
        return args.length > 0 && HowLongAdminCommands.LIST.equals(args[0]);
    }

    public Boolean updateMode() {
        return args.length > 0 && HowLongAdminCommands.UPDATE.equals(args[0]);
    }

    public Boolean deleteMode() {
        return args.length > 0 && HowLongAdminCommands.DELETE.equals(args[0]);
    }

    private void printUsage() {

    }

}