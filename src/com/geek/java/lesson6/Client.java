package com.geek.java.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5664;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Client connected to the server");
            showServerMessages();
            readMessagesFromConsole();
        } catch (IOException e) {
            System.err.println("Client can not connect to the server");
        }
    }

    private static void showServerMessages() {
        Thread showServerMessagesThread = new Thread(() -> {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    System.out.println("> Server: " + message);
                    if (message.startsWith("/end")) {
                        System.out.println("End by server.");
                        System.exit(0);
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Error showServerMessages");
                    System.exit(0);
                    break;
                }
            }
        });
        showServerMessagesThread.setDaemon(true);
        showServerMessagesThread.start();
    }

    private static void readMessagesFromConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String message = scanner.nextLine();
                dataOutputStream.writeUTF(message);
                if (message.startsWith("/end")) {
                    System.out.println("End.");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error from readMessagesFromConsole");
            }
        }
    }
}
