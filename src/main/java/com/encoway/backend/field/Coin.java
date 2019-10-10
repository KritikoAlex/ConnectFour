package com.encoway.backend.field;

import com.encoway.backend.field.color.Colors;

public enum Coin {

    YELLOW(Colors.ANSI_YELLOW),
    RED(Colors.ANSI_RED);

    private String color;

    private Coin(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
