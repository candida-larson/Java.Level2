package com.gb.clientchat.clientchat;

import com.gb.clientchat.clientchat.dialogs.Dialogs;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private static Network INSTANCE;
    private final String HOSTNAME = "localhost";
    private final int PORT = 8187;
    private Socket socket;
    private boolean connected = false;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Thread readMessagesThread;

    private Network() {

    }

    public static Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }
        return INSTANCE;
    }

    public boolean connect() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            readMessages();
            connected = true;
            return true;
        } catch (IOException e) {
            System.err.println("Can not connect to the server");
            return false;
        }
    }

    private void readMessages() {
        readMessagesThread = new Thread(() -> {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    if (message.startsWith("/authok")) {
                        String username = message.split(" ")[1];
                        System.out.println("Success auth: " + username);
                        Platform.runLater(() -> {
                            ClientChatApplication.getInstance().switchToMainChatWindow(username);
                        });
                    } else {
                        Platform.runLater(() -> {
                            Dialogs.AuthError.INVALID_CREDENTIALS.show();
                        });
                    }
                } catch (IOException e) {
                    System.err.println("Error read message from server");
                    break;
                }

            }
        });
        readMessagesThread.start();
    }

    public void sendMessage(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }

}
