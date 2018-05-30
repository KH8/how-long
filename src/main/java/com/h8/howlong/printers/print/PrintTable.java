package com.h8.howlong.printers.print;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrintTable {

    private List<List<String>> cells;

    private Integer cellWidth;

    private PrintTable() {
        this.cells = new ArrayList<>();
    }

    public static PrintTable builder() {
        return new PrintTable();
    }

    public PrintTable withCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public PrintTable addRow(List<String> row) {
        this.cells.add(row);
        return this;
    }

    public String serialize() {
        return cells.stream()
                .map(this::serializeRow)
                .map(r -> String.format("| %s |", r))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String serializeRow(List<String> row) {
        return row.stream()
                .map(this::adjustWidth)
                .collect(Collectors.joining(" | "));
    }

    private String adjustWidth(String s) {
        return s != null ? StringUtils.rightPad(s, cellWidth) : s;
    }

}
