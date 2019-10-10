package com.encoway.networking;

import com.encoway.utils.Vector2d;

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
        System.out.println("ip addresses:");
        System.out.println("local: " + this.getLocalIp());
        System.out.println("public: " + this.getPublicIp());
        System.out.print("\ndo you want to connect to your opponent?(y/n): ");
        if (new Scanner(System.in).nextLine().charAt(0) == 'y') {
            System.out.print("enter ip: ");
            this.send(new Scanner(System.in).nextLine(), 80, new Packet(PacketType.CONNECT_TO_OPPONENT, null));
            System.out.println("waiting for response...");
        } else {
            System.out.println("waiting for incoming connections...");
            while (!this.listener.gotPacket)
                Thread.sleep(250);
            System.out.println("connected");
        }
    }

    public String getPublicIp() throws IOException {
        return new BufferedReader(new InputStreamReader(new URL("https://checkip.amazonaws.com").openStream())).readLine();
    }

    public String getLocalIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void send(String ip, int port, Packet data) throws IOException {
        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data);
        socket.close();
    }
}
