package com.encoway.networking;

import java.io.Serializable;

public class Packet implements Serializable {
    PacketType packetType;
    String data;
    public Packet(PacketType packetType, String data) {
        this.packetType = packetType;
        this.data = data;
    }
}
