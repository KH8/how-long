package com.h8.howlong.admin.utils;

public class ArgumentResolutionFailedException extends Exception {

    public ArgumentResolutionFailedException(String message) {
        super(message);
    }

    ArgumentResolutionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
