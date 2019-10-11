package com.encoway;

import com.encoway.backend.field.Coin;
import com.encoway.backend.field.color.Colors;
import com.encoway.backend.grid.Grid;
import com.encoway.networking.Listener;
import com.encoway.networking.Networking;
import com.encoway.networking.Packet;
import com.encoway.networking.PacketType;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFourMain {

    public static Grid grid = new Grid();
    public static boolean won = false;
    private static Coin currentPlayer = Coin.YELLOW;
    public static int id = 0;
    public static Coin winnerCoin;
    public static Networking networking;

    public static void main(String[] args) throws IOException, InterruptedException {
        networking = new Networking();
        networking.instruct();
        grid.print();
        while (!won) {
            Thread.sleep(10);
            while (Listener.gotPacket) {
                //Both!
                //Sender Side
                if (Listener.packet.getPacketType() == PacketType.TAKE_CONTROL) {
                    Thread.sleep(10);
                    Listener.gotPacket = false;
                    int row = insertCoinViaControl();
                    if (!won) {
                        networking.send(Listener.lastOpponentIp, 3389, new Packet(PacketType.PLACE_CHIP, row));
                        Thread.sleep(250);
                        networking.send(Listener.lastOpponentIp, 3389, new Packet(PacketType.TAKE_CONTROL, 0));
                        grid.print();
                        switchPlayer();
                    }
                    //Receipt Side
                } else if (Listener.packet.getPacketType() == PacketType.PLACE_CHIP) {
                    Packet packet = Listener.packet;
                    int row = packet.getData();
                    grid.insertCoin(row, 0, currentPlayer);
                    grid.print();
                    switchPlayer();
                } else if (Listener.packet.getPacketType() == PacketType.WIN) {
                    grid.insertCoin(Listener.packet.getData(), 0, currentPlayer);
                    grid.print();
                    System.out.println("The player " + currentPlayer.getColor() + (id == 1 ? "0" : "1") + Colors.ANSI_RESET + " has won!");
                    System.exit(0);
                }
            }
        }
        System.out.println("The player " + currentPlayer.getColor() + id + Colors.ANSI_RESET + " has won!");
        System.exit(0);
    }

    private static int insertCoinViaControl() {
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
        } while (!valid);

        grid.insertCoin(row, 0, currentPlayer);
        return row;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == Coin.YELLOW ? Coin.RED : Coin.YELLOW);
    }
}
