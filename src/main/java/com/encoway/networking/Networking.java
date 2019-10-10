package com.encoway.networking;

import java.io.*;
import java.net.Socket;

public class Networking {
    private Listener listener;
    public Networking() {
        this.listener = new Listener();
        this.listener.start();


    }

    public void send(String ip, int port, String data) throws IOException {
        Socket client = new Socket(ip, port);

        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        out.writeUTF(data);
        client.close();
    }
}
