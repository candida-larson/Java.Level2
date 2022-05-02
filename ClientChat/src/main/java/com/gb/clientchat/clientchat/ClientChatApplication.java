package com.gb.clientchat.clientchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChatApplication extends Application {
    private Stage chatStage;
    private Stage authStage;
    private FXMLLoader authLoader;

    @Override
    public void start(Stage stage) throws IOException {
        this.chatStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChatApplication.class.getResource("client-chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Chat");
        stage.setScene(scene);
        loadUserList(fxmlLoader);
        stage.centerOnScreen();
        stage.show();
        initAuthDialog();
    }

    private void initAuthDialog() throws IOException {
        authLoader = new FXMLLoader();
        authLoader.setLocation(ClientChatApplication.class.getResource("auth-view.fxml"));
        authStage = new Stage();
        authStage.initOwner(chatStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authLoader.load()));
        authStage.show();
    }

    private void loadUserList(FXMLLoader fxmlLoader) {
        ClientChatController clientChatController = fxmlLoader.getController();
        clientChatController.userList.getItems().addAll("Pavel", "Mihael");
    }

    public static void main(String[] args) {
        launch();
    }
}