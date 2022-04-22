package com.gb.clientchat.clientchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChatApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Chat");
        stage.setScene(scene);
        loadUserList(fxmlLoader);
        stage.centerOnScreen();
        stage.show();
    }

    private void loadUserList(FXMLLoader fxmlLoader) {
        ClientChatController clientChatController = fxmlLoader.getController();
        clientChatController.userList.getItems().addAll("Pavel", "Mihael");
    }

    public static void main(String[] args) {
        launch();
    }
}