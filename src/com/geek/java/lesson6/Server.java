package com.geek.java.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5664)) {
            System.out.println("Start accept client connection");
            socket = serverSocket.accept();
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String message = scanner.next();
                dataOutputStream.writeUTF(message);
                if (message.startsWith("/end")) {
                    break;
                }
            } catch (IOException e) {
                System.err.println("Cannot send message from server");
                break;
            }
        }
    }

    private static void showClientMessages() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    System.out.println("> From client: " + message);
                    if (message.startsWith("/end")) {
                        break;
                    }
                } catch (IOException e) {
                    System.err.println("Error showClientMessage");
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
