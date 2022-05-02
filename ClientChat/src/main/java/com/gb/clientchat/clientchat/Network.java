package com.gb.clientchat.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private Network INSTANCE;
    private final String HOSTNAME = "localhost";
    private final int PORT = 8187;
    private Socket socket;
    private boolean connected = false;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Network() {

    }

    public Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }
        return INSTANCE;
    }

    private boolean connect() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            connected = true;
            return true;
        } catch (IOException e) {
            System.err.println("Can not connect to the server");
            return false;
        }
    }

}
