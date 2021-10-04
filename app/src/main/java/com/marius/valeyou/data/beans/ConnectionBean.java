package com.marius.valeyou.data.beans;

public class ConnectionBean {
    private State type;
    private boolean isConnected;

    public ConnectionBean(State type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public State getType() {
        return type;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public enum State {WIFI, MOBILE, NONE}
}
