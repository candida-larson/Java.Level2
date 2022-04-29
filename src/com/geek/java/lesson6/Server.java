package com.geek.java.lesson6;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5664)) {
            System.err.println("Start accept client connection");
            serverSocket.accept();
            System.err.println("Finish accept client connection");

        } catch (IOException e) {
            System.err.println("Server can not bind port");
        }
    }
}
