package com.h8.howlong.admin.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandResult {

    private final String message;

    private final CommandResultStatus status;

}
