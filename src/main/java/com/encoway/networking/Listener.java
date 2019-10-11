package com.encoway.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {

    public static String lastOpponentIp;
    public static Packet packet;
    public static boolean gotPacket;

    public void run() {
        ServerSocket serverSocket = null;
        InputStream inputStream = null;
        packet = null;
        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        gotPacket = false;

        try {
            serverSocket = new ServerSocket(3389);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
            this.lastOpponentIp = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");

            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                objectInputStream = new ObjectInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                assert objectInputStream != null;
                this.packet = (Packet) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            //packet weitergeben
            gotPacket = true;
        }
    }
}
