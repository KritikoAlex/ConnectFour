package com.encoway;

import com.encoway.backend.field.Coin;
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

    public static void main(String[] args) throws IOException, InterruptedException {
        Networking networking = new Networking();
        networking.instruct();

        grid.print();
        System.out.println("Here!");
        while (!won) {
            System.out.println("NOW!");
            while(Listener.gotPacket) {
                //Both!
                System.out.println("Test!");
                //Sender Side
                if(Listener.packet.getPacketType() == PacketType.TAKE_CONTROL) {
                    System.out.println("Hey!");
                    int row = insertCoinViaControl();
                    Listener.gotPacket = false;
                    networking.send(Listener.lastOpponentIp, 80, new Packet(PacketType.PLACE_CHIP, row));
                    Thread.sleep(250);
                    networking.send(Listener.lastOpponentIp, 80, new Packet(PacketType.TAKE_CONTROL, 0));
                    grid.print();
                //Receipt Side
                }else if(Listener.packet.getPacketType() == PacketType.PLACE_CHIP){
                    Packet packet = Listener.packet;
                    int row = packet.getData();
                    grid.insertCoin(row, 0, currentPlayer);
                    grid.print();
                }
            }
        }
        System.out.println("You won!");
    }

    private static int insertCoinViaControl(){
        System.out.println("Gib eine Spalte ein: ");
        System.out.println("Packet Type: " + Listener.packet.getPacketType().toString());
        System.out.println("Received Info: " + Listener.gotPacket);
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
}
