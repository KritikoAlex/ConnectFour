package com.encoway;

import com.encoway.backend.field.Coin;
import com.encoway.backend.grid.Grid;
import com.encoway.networking.Listener;
import com.encoway.networking.Networking;
import com.encoway.networking.Packet;
import com.encoway.networking.PacketType;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

public class ConnectFourMain {

    public static Grid grid = new Grid();
    public static boolean won = false;
    private static Coin currentPlayer = Coin.YELLOW;

    public static void main(String[] args) throws IOException, InterruptedException {
        Networking networking = new Networking();
        networking.instruct();

        grid.print();
        while (!won && Listener.gotPacket && Listener.packet.getPacketType() == PacketType.TAKE_CONTROL) {
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
            Listener.gotPacket = false;
            networking.send(Listener.lastOpponentIp, 80, new Packet(PacketType.PLACE_CHIP, row));
            networking.send(Listener.lastOpponentIp, 80, new Packet(PacketType.TAKE_CONTROL, 0));
            grid.print();
        }
        System.out.println("You won!");
    }
}
