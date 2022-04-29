package com.geek.java.lesson6;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5664);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client connected to the server");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = scanner.next();
                dataOutputStream.writeUTF(message);

                if (message.startsWith("/end")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Client can not connect to the server");
        }
    }
}
