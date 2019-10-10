package com.encoway;

import com.encoway.backend.grid.Grid;
import com.encoway.networking.Networking;

import java.io.IOException;

public class ConnectFourMain {

    public static Grid grid = new Grid();

    public static void main(String[] args) throws IOException, InterruptedException {
        Networking networking = new Networking();
        networking.instruct();
        grid.print();
    }

}
