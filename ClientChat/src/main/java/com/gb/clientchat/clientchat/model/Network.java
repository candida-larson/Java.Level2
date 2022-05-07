package com.gb.clientchat.clientchat.model;

import com.gb.clientchat.clientchat.ClientChatApplication;
import com.gb.clientchat.clientchat.dialogs.Dialogs;
import com.gb.clientchat.co.Command;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Network {
    private static Network INSTANCE;
    private final String HOSTNAME = "localhost";
    private final int PORT = 8187;
    private Socket socket;
    private boolean connected = false;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
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
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
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
                    String message = objectInputStream.readUTF();
                    System.out.println(">> MESSAGE FROM NETWORK:: " + message);

                    if (message.startsWith("/authok")) {
                        String username = message.split(" ")[1];
                        System.out.println("Success auth: " + username);
                        Platform.runLater(() -> {
                            ClientChatApplication.getInstance().switchToMainChatWindow(username);
                        });
                    } else if (message.startsWith("/autherror")) {
                        Platform.runLater(() -> {
                            Dialogs.AuthError.INVALID_CREDENTIALS.show();
                        });
                    } else if (message.startsWith("/mfrom")) {
                        ClientChatApplication.getInstance().getChatController().processMessageFromOtherClient(message);
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
        System.out.println(">> SEND MESSAGE: " + message);
        objectOutputStream.writeUTF(message);
    }

}
