package com.encoway.backend.grid;

import com.encoway.backend.field.Coin;
import com.encoway.backend.field.Field;

public class Grid {

    private Field[][] grid = new Field[6][7];

    public Grid() {
        for(int i = 0; i < grid.length; i++){
            for(int d = 0; d < grid[i].length; d++){
                Field field = new Field();
                field.setCoin(Coin.YELLOW);
                field.setFull(true);
                grid[i][d] = field;
            }
        }
    }

    public void print() {
        for (Field[] horizontalRow : grid) {
            StringBuilder row = new StringBuilder();
            row.append("│");
            for (Field field : horizontalRow) {
                row.append(field.print()+"│");
            }
            System.out.println(row);
        }
    }

}
