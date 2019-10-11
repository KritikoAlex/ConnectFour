package com.encoway.networking;

import com.encoway.utils.Vector2d;

import java.io.Serializable;

public class Packet implements Serializable {
    PacketType packetType;
    Vector2d data;

    public PacketType getPacketType() {
        return packetType;
    }

    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
    }

    public Vector2d getData() {
        return data;
    }

    public void setData(Vector2d data) {
        this.data = data;
    }

    public Packet(PacketType packetType, Vector2d data) {
        this.packetType = packetType;
        this.data = data;
    }
}
