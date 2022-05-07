package com.gb.clientchat.clientchat.controllers;

import com.gb.clientchat.clientchat.ClientChatApplication;
import com.gb.clientchat.clientchat.dialogs.Dialogs;
import com.gb.clientchat.clientchat.model.Network;
import com.gb.clientchat.clientchat.model.ReadMessageListener;
import com.gb.clientchat.co.Command;
import com.gb.clientchat.co.CommandType;
import com.gb.clientchat.co.commands.AuthOkCommandData;
import com.gb.clientchat.co.commands.ErrorCommandData;
import javafx.application.Platform;
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

        try {
            Network.getInstance().sendAuthMessage(login, password);
        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
            System.err.println("Error send auth message");
        }
    }

    public void initMessageHandler() {
        Network.getInstance().addReadMessageListener(command -> {

            if (command.getType() == CommandType.AUTH_OK) {
                AuthOkCommandData data = (AuthOkCommandData) command.getData();
                Platform.runLater(() -> {
                    ClientChatApplication.getInstance().switchToMainChatWindow(data.getLogin());
                });
            } else if (command.getType() == CommandType.AUTH_ERROR) {
                Platform.runLater(Dialogs.AuthError.INVALID_CREDENTIALS::show);
            } else if (command.getType() == CommandType.ERROR) {
                ErrorCommandData data = (ErrorCommandData) command.getData();
                System.err.println(data.getErrorMessage());
            }

        });
    }

    public void close() {

    }

}
