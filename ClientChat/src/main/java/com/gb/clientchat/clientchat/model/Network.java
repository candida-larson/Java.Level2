package com.gb.clientchat.clientchat.model;

import com.gb.clientchat.co.Command;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Network {
    private static Network INSTANCE;
    private final String HOSTNAME = "localhost";
    private final int PORT = 8187;
    private Socket socket;
    private boolean connected = false;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Thread readMessagesThread;

    private List<ReadMessageListener> listeners = new CopyOnWriteArrayList<>();

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
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
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
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    Command command = readCommand();
                    if (command == null) {
                        continue;
                    }

                    for (ReadMessageListener listener : listeners) {
                        listener.processReceivedCommand(command);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Server can not read message");
                    close();
                    return;
                }
            }
        });
        readMessagesThread.setDaemon(true);
        readMessagesThread.start();
    }

    public void sendPrivateMessage(String receiver, String message) throws IOException {
        System.out.println(">> sendPrivateMessage");
        sendCommand(Command.privateMessageCommand(receiver, message));
    }

    public void sendCommand(Command command) throws IOException {
        objectOutputStream.writeObject(command);
    }

    private Command readCommand() throws IOException, ClassNotFoundException {
        return (Command) objectInputStream.readObject();
    }

    public ReadMessageListener addReadMessageListener(ReadMessageListener readMessageListener) {
        listeners.add(readMessageListener);
        return readMessageListener;
    }

    public void removeReadMessageListener(ReadMessageListener readMessageListener) {
        listeners.remove(readMessageListener);
    }

    public void sendAuthMessage(String login, String password) throws IOException {
        sendCommand(Command.authCommand(login, password));
    }

    public void sendPublicMessage(String text) throws IOException {
        sendCommand(Command.publicMessageCommand(text));
    }

    public void close() {
        try {
            socket.close();
            connected = false;
            readMessagesThread.interrupt();
        } catch (IOException e) {
            System.err.println("Close error");
        }
    }

}
