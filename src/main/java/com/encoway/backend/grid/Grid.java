package com.encoway.backend.grid;

import com.encoway.ConnectFourMain;
import com.encoway.backend.field.Coin;
import com.encoway.backend.field.Field;
import com.encoway.networking.Listener;
import com.encoway.networking.Networking;
import com.encoway.networking.Packet;
import com.encoway.networking.PacketType;

import java.io.IOException;

public class Grid {

    private Field[][] grid = new Field[6][7];

    public Grid() {
        for (int i = 0; i < grid.length; i++) {
            for (int d = 0; d < grid[i].length; d++) {
                Field field = new Field();
                field.setCoin(Coin.NOTHING);
                grid[i][d] = field;
            }
        }
    }

    public void insertCoin(int height, int row, Coin controlCoin) {
        ConnectFourMain.grid.print();
        Field field = null;
        //Try getting the field at first since it might not exist
        try {
            field = grid[row][height];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Test here now!2 ");
            ConnectFourMain.won = isFourInARow(row - 1, height, controlCoin);
            return;
        }
        //If the field is not full then add in a coin
        if (!field.isFull()) {
            field.setCoin(controlCoin);
            try {
                if (row > 0) {
                    grid[row - 1][height].setCoin(Coin.NOTHING);
                    insertCoin(height, row + 1, controlCoin);
                } else {
                    insertCoin(height, row + 1, controlCoin);
                }
            } catch (IndexOutOfBoundsException e) {
                insertCoin(height, row + 1, controlCoin);
            }
        } else if (grid[row][height] != null) {
            System.out.println("Test here now!");
            ConnectFourMain.won = isFourInARow(row - 1, height, controlCoin);
        }
    }

    public void print() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch (IOException | InterruptedException e){

        }
        System.out.println(" \n ");
        for (int i = 0; i < grid.length; i++) {
            Field[] horizontalRow = grid[i];
            StringBuilder row = new StringBuilder();
            if(i == 0){
                row.append("        ╔ ");

            }else if(i == 5){
                row.append("        ╚ ");
            }else {
                row.append("        ╟ ");
            }
            for (int d = 0; d < horizontalRow.length; d++) {
                Field field = horizontalRow[d];
                if (i == 0) {
                    if (d == 6) {
                        row.append(field.print() + " ╗ ");
                    } else {
                        row.append(field.print() + " ╤ ");
                    }
                } else if (i == 5) {
                    if (d == 6) {
                        row.append(field.print() + " ╝ ");
                    }else{
                        row.append(field.print() + " ╧ ");
                    }
                } else if (i > 0 && i < 5 && d == 6) {
                    row.append(field.print() + " ╣ ");
                } else {
                    row.append(field.print() + " ┼ ");
                }
            }
                System.out.println(row);
        }
    }

    private int lengthOfLineStartingAt(int x, int y, int xStep, int yStep, int length, Coin controlCoin) {
        if (length >= 4) {
            return length;
        }
        int i = 0;
        Field field = null;
        try {
            field = grid[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return length;
        }

        Coin fieldCoin = field.getCoin();
        if (fieldCoin.equals(controlCoin)) {
            length++;
            i = lengthOfLineStartingAt(x + xStep, y + yStep, xStep, yStep, length, controlCoin);
        } else {
            return length;
        }
        return i;
    }

    private int lengthOfLineContaining(int x, int y, int xStep, int yStep, Coin coin) {
        int first = lengthOfLineStartingAt(x, y, xStep, yStep, 0, coin);
        int second = lengthOfLineStartingAt(x, y, -xStep, -yStep, 0, coin);
        int result = first + second - 1;
        return result;
    }

    public boolean isFourInARow(int x, int y, Coin coin) {
        return
                lengthOfLineContaining(x, y, 1, 0, coin) >= 4 ||
                lengthOfLineContaining(x, y, 1, 1, coin) >= 4 ||
                lengthOfLineContaining(x, y, 1, -1, coin) >= 4 ||
                lengthOfLineContaining(x, y, 0, 1, coin) >= 4;

    }
}
