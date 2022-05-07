package com.gb.clientchat.clientchat.controllers;

import com.gb.clientchat.clientchat.model.Network;
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
            String recipient = "NONE";
            if (!userList.getSelectionModel().isEmpty()) {
                recipient = userList.getSelectionModel().getSelectedItem().toString();
            }
            appendMessageToChat("", messageTextArea.getText());

            try {
                Network.getInstance().sendMessage(String.format("/w %s %s", recipient, messageTextArea.getText()));
            } catch (IOException e) {
                System.err.println("Cannot send message from textarea");
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

    public void processMessageFromOtherClient(String message) {
        String[] parts = message.split(" ", 3);
        appendMessageToChat(parts[1], parts[2]);
    }

}