package com.encoway;

import com.encoway.backend.field.Coin;
import com.encoway.backend.grid.Grid;

public class ConnectFourMain {

    public static Grid grid = new Grid();

    public static void main(String[] args) {
        grid.insertCoin(1, 0, Coin.RED);
        grid.insertCoin(1, 0, Coin.YELLOW);
        grid.insertCoin(1, 0, Coin.YELLOW);
        grid.insertCoin(0, 0, Coin.RED);
        grid.print();
    }

}
