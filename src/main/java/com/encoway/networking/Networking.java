package com.encoway.networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Networking {
    Listener listener;

    public Networking() {
        this.listener = new Listener();
        this.listener.start();
    }

    public void instruct() throws IOException, InterruptedException {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch (IOException | InterruptedException e){ }
        String[] ips = new String[2];
        ips[0] = this.getLocalIp();
        ips[1] = this.getPublicIp();
        System.out.println("ip addresses:");
        System.out.println("local: " + ips[0]);
        System.out.println("public: " + ips[1]);
        System.out.print("\nDo you want to connect to your opponent?(y/n): ");
        if (new Scanner(System.in).nextLine().charAt(0) == 'y') {
            System.out.print("enter ip: ");
            this.send(new Scanner(System.in).nextLine(), 80, new Packet(PacketType.CONNECT_TO_OPPONENT, 0));
            System.out.println("waiting for response...");

            while (!Listener.gotPacket) {
                Thread.sleep(250);
            }
            Listener.gotPacket = false;

            System.out.println("connected");
        } else {
            System.out.println("waiting for incoming connections...");
            while (!this.isValidPacket()) {
                Thread.sleep(250);
            }
            this.send(Listener.lastOpponentIp, 80, new Packet(PacketType.ACCEPT_INVITATION, 0));
            System.out.println("connected");
            Thread.sleep(250);
            this.send(Listener.lastOpponentIp, 80, new Packet(PacketType.TAKE_CONTROL, 0));

        }
    }

    private String getPublicIp() {
        try {
            return new BufferedReader(new InputStreamReader(new URL("https://checkip.amazonaws.com").openStream())).readLine();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private String getLocalIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void send(String ip, int port, Packet data) throws IOException {
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data);
        socket.close();
    }

    private boolean isValidPacket() {
        if (!Listener.gotPacket)
            return false;

        return Listener.packet.packetType == PacketType.CONNECT_TO_OPPONENT;
    }
}
