package com.h8.howlong.utils.print;

public final class PrintBuilder {

    private final static String LS = System.lineSeparator();

    private final StringBuilder sb;

    private PrintBuilder() {
        sb = new StringBuilder();
        sb.append(LS);
    }

    public static PrintBuilder builder() {
        return new PrintBuilder();
    }

    public PrintBuilder ln() {
        return ln("");
    }

    public PrintBuilder ln(String s) {
        sb.append(s);
        sb.append(LS);
        return this;
    }

    public String build() {
        var s = sb.toString();
        s = resolveColors(s);
        return s;
    }

    private String resolveColors(String s) {
        for (PrintColor c : PrintColor.values()) {
            s = s.replaceAll(c.getAlias(), c.getAnsiCode());
        }
        return s;
    }

}
