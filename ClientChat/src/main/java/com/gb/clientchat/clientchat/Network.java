package com.gb.clientchat.clientchat;

public class Network {
    private Network INSTANCE;

    private Network() {

    }

    public Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }
        return INSTANCE;
    }
}
