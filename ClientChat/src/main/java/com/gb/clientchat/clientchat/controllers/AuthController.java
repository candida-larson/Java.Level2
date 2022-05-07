package com.gb.clientchat.clientchat.controllers;

import com.gb.clientchat.clientchat.dialogs.Dialogs;
import com.gb.clientchat.clientchat.model.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {
    @FXML
    public TextField authLogin;
    @FXML
    public TextField authPassword;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = authLogin.getText();
        String password = authPassword.getText();

        if (login == null || password == null || login.isBlank() || login.isBlank()) {
            Dialogs.AuthError.EMPTY_CREDENTIALS.show();
            return;
        }

        if (!Network.getInstance().connect()) {
            Dialogs.NetworkError.SERVER_CONNECT.show();
            return;
        }

        sendAuthMessage(login, password);
    }

    private void sendAuthMessage(String login, String password) {
        try {
            Network.getInstance().sendMessage(String.format("/auth %s %s", login, password));
        } catch (IOException e) {
            System.err.println("Error send auth message to server");
        }
    }

    public void close(){

    }

}
