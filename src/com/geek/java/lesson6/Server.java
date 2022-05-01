package com.geek.java.lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final int PORT = 5664;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Start accept client connection");
            Socket socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Finish accept client connection");
            showClientMessages();
            readMessagesFromConsole();

        } catch (IOException e) {
            System.err.println("Server can not bind port");
        }
    }

    private static void readMessagesFromConsole() {
        Scanner messageScanner = new Scanner(System.in);
        while (true) {
            try {
                String message = messageScanner.nextLine();
                dataOutputStream.writeUTF(message);
                if (message.startsWith("/end")) {
                    System.out.println("End.");
                    break;
                }
            } catch (IOException e) {
                System.err.println("Cannot send message from server");
                break;
            }
        }
    }

    private static void showClientMessages() {
        Thread showClientMessagesThread = new Thread(() -> {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    System.out.println("> Client: " + message);
                    if (message.startsWith("/end")) {
                        System.out.println("End by client.");
                        System.exit(0);
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Error showClientMessage");
                    System.exit(0);
                    break;
                }
            }
        });
        showClientMessagesThread.setDaemon(true);
        showClientMessagesThread.start();
    }
}
