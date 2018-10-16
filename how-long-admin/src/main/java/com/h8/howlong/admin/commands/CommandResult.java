package com.h8.howlong.admin.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandResult {

    private final String message;

    private final CommandResultStatus status;

    public static CommandResult ok(String message) {
        return new CommandResult(message, CommandResultStatus.SUCCESS);
    }

    public static CommandResult error(String message) {
        return new CommandResult(message, CommandResultStatus.ERROR);
    }

    public boolean isSuccessful() {
        return CommandResultStatus.SUCCESS.equals(this.status);
    }

}
