package com.gb.clientchat.clientchat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.text.DateFormat;
import java.util.Date;

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
            messagesListTextArea.appendText(DateFormat.getDateTimeInstance().format(new Date()) + System.lineSeparator());
            if (!userList.getSelectionModel().isEmpty()) {
                messagesListTextArea.appendText(userList.getSelectionModel().getSelectedItem().toString() + System.lineSeparator());
            }
            messagesListTextArea.appendText(messageTextArea.getText().trim() + System.lineSeparator().repeat(2));
            messageTextArea.clear();
            messageTextArea.requestFocus();
        }
    }
}