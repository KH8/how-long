package com.h8.howlong.printers.print;

import lombok.Getter;

@Getter
public enum PrintColor {
    GREEN("<c", "\u001B[36m"),
    YELLOW("<y", "\u001B[33m"),
    RESET(">", "\u001B[0m");

    private String alias;

    private String ansiCode;

    PrintColor(String alias, String ansiCode) {
        this.alias = alias;
        this.ansiCode = ansiCode;
    }

    @Override
    public String toString() {
        return alias;
    }

}
