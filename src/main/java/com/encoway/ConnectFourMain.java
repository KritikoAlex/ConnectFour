package com.encoway;

import com.encoway.backend.field.Coin;
import com.encoway.backend.grid.Grid;
import com.encoway.networking.Networking;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFourMain {

    public static Grid grid = new Grid();
    public static boolean won = false;
    private static Coin currentPlayer = Coin.YELLOW;

    public static void main(String[] args) throws IOException, InterruptedException {
        /*Networking networking = new Networking();
        networking.instruct();
        grid.print();
        */
        grid.print();
        while(!won) {
            System.out.println("Gib eine Spalte ein: ");
            int row = 0;
            boolean valid = false;

            do {
                try {
                    row = new Scanner(System.in).nextInt();
                    valid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Nutze bitte nur Nummern!");
                    valid = false;
                }
            }while (!valid);

            grid.insertCoin(row, 0, currentPlayer);
            switchPlayer();
            grid.print();
        }
        System.out.println("You won!");
    }

    private static void switchPlayer(){
        currentPlayer = (currentPlayer.equals(Coin.YELLOW) ? Coin.RED : Coin.YELLOW);
    }



}
