package com.encoway.backend.grid;

import com.encoway.backend.field.Coin;
import com.encoway.backend.field.Field;

public class Grid {

    private Field[][] grid = new Field[6][7];

    public Grid() {
        for(int i = 0; i < grid.length; i++){
            for(int d = 0; d < grid[i].length; d++){
                Field field = new Field();
                grid[i][d] = field;
            }
        }
    }

    public void insertCoin(int height, int row, Coin coin){
        Field field = null;
        //Try getting the field at first since it might not exist
        try {
            field = grid[row][height];
            System.out.println("The field "+row+" "+height+" exists!");
        }catch (ArrayIndexOutOfBoundsException e){
            return;
        }
        //If the field is not full then add in a coin
        if(!field.isFull()){
            System.out.println("Added to field "+row+" "+height);
            field.setCoin(coin);
            try{
                if(row > 0) {
                    System.out.println("Made it disappear...");
                    grid[row-1][height].setCoin(Coin.NOTHING);
                    insertCoin(height, row+1, coin);
                }else{
                    insertCoin(height, row+1, coin);
                }
            }catch(IndexOutOfBoundsException e){
                insertCoin(height, row+1, coin);
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
