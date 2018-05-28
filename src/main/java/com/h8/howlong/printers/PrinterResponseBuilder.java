package com.h8.howlong.printers;

final class PrinterResponseBuilder {

    private final static String LS = System.lineSeparator();

    private final StringBuilder sb;

    private PrinterResponseBuilder() {
        sb = new StringBuilder();
        sb.append(LS);
    }

    static PrinterResponseBuilder builder() {
        return new PrinterResponseBuilder();
    }

    PrinterResponseBuilder ln() {
        return ln("");
    }

    PrinterResponseBuilder ln(String s) {
        sb.append(s);
        sb.append(LS);
        return this;
    }

    String build() {
        return sb.toString();
    }

}
