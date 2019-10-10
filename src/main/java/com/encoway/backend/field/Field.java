package com.encoway.backend.field;

import com.encoway.backend.field.color.Colors;

public class Field {

    private boolean isFull;
    private Coin coin;

    public String print(){
        return (isFull ? coin.getColor()+"■"+ Colors.ANSI_RESET : " ");
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
}
