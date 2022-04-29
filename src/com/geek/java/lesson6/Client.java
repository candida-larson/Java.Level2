package com.geek.java.lesson6;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5664);
            System.out.println("Client connected to the server");
        } catch (IOException e) {
            System.err.println("Client can not connect to the server");
        }
    }
}
