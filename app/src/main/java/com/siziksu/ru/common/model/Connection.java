package com.siziksu.ru.common.model;

public class Connection {

    private boolean connected;
    private String name;

    public Connection(boolean connected, String name) {
        this.connected = connected;
        this.name = name;
    }

    public boolean isConnected(Boolean... arg) {
        return arg != null && arg.length > 0 ? arg[0] : connected;
    }

    public String getName() {
        return name;
    }
}

