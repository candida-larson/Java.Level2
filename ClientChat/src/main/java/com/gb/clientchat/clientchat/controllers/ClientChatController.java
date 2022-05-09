package com.gb.clientchat.clientchat.controllers;

import com.gb.clientchat.clientchat.dialogs.Dialogs;
import com.gb.clientchat.clientchat.model.Network;
import com.gb.clientchat.clientchat.model.ReadMessageListener;
import com.gb.clientchat.co.Command;
import com.gb.clientchat.co.CommandType;
import com.gb.clientchat.co.commands.ClientMessageCommandData;
import com.gb.clientchat.co.commands.UpdateUserListCommandData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ClientChatController {

    @FXML
    public TextArea messageTextArea;
    @FXML
    public Button sendMessageButton;
    @FXML
    public TextArea messagesListTextArea;
    @FXML
    public ListView userList;

    public void sendMessage(ActionEvent actionEvent) {
        if (!messageTextArea.getText().isEmpty()) {
            String recipient = null;
            if (!userList.getSelectionModel().isEmpty()) {
                recipient = userList.getSelectionModel().getSelectedItem().toString();
            }
            appendMessageToChat("", messageTextArea.getText());

            try {
                if (recipient != null) {
                    Network.getInstance().sendPrivateMessage(recipient, messageTextArea.getText());
                } else {
                    Network.getInstance().sendPublicMessage(messagesListTextArea.getText());
                }
            } catch (IOException e) {
                Dialogs.NetworkError.SEND_MESSAGE.show();
            }

            messageTextArea.clear();
            messageTextArea.requestFocus();
        }
    }

    public void appendMessageToChat(String sender, String message) {
        messagesListTextArea.appendText(DateFormat.getDateTimeInstance().format(new Date()) + System.lineSeparator());
        if (sender != null && !sender.isEmpty()) {
            messagesListTextArea.appendText("From: " + sender + System.lineSeparator());
        }
        messagesListTextArea.appendText(message.trim() + System.lineSeparator());
        messagesListTextArea.appendText("-".repeat(42) + System.lineSeparator());
    }

    public void initMessageHandler() {
        Network.getInstance().addReadMessageListener(command -> {
            if (command.getType() == CommandType.CLIENT_MESSAGE) {
                ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                appendMessageToChat(data.getSender(), data.getMessage());
            } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                System.out.println("Update users list");
                UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                Platform.runLater(() -> userList.setItems(FXCollections.observableList(data.getUsers())));
            }
        });
    }
}