package com.encoway.networking;

import java.io.Serializable;

public class Packet implements Serializable {
    PacketType packetType;
    int data;

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Packet(PacketType packetType, int data) {
        this.packetType = packetType;
        this.data = data;
    }
}
