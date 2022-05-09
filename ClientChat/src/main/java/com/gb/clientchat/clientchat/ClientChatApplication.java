package com.gb.clientchat.clientchat;

import com.gb.clientchat.clientchat.controllers.AuthController;
import com.gb.clientchat.clientchat.controllers.ClientChatController;
import com.gb.clientchat.clientchat.model.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientChatApplication extends Application {
    private static ClientChatApplication INSTANCE;
    private Stage chatStage;
    private Stage authStage;
    private FXMLLoader authLoader;
    private FXMLLoader chatLoader;
    private String authorizedLogin;
    private Timer connectionTimer;

    @Override
    public void init() {
        INSTANCE = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.chatStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChatApplication.class.getResource("client-chat-view.fxml"));
        this.chatLoader = fxmlLoader;
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Chat");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        loadUserList(fxmlLoader);
        initAuthDialog();

        getChatController().initMessageHandler();
        getAuthController().initMessageHandler();

        Network.getInstance().connect();
        closeConnectionAfterTime();
    }

    private void closeConnectionAfterTime() {
        if (connectionTimer != null) {
            connectionTimer.cancel();
        }
        connectionTimer = new Timer();
        connectionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("RUN closeConnectionAfterTime in GUI");
                if (getAuthorizedLogin() == null || getAuthorizedLogin().isEmpty()) {
                    Network.getInstance().close();
                    System.out.println(">> Network close() ");
                }
                connectionTimer.cancel();
            }
        }, 1000 * 120);
    }

    private void initAuthDialog() throws IOException {
        authLoader = new FXMLLoader();
        authLoader.setLocation(ClientChatApplication.class.getResource("auth-view.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(chatStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));
        authStage.show();
    }

    private void loadUserList(FXMLLoader fxmlLoader) {
        ClientChatController clientChatController = fxmlLoader.getController();
        // clientChatController.userList.getItems().addAll("login1", "login2", "login3");
    }

    public void switchToMainChatWindow(String userName) {
        getChatStage().setTitle(userName);
        getAuthController().close();
        getAuthStage().close();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public ClientChatController getChatController() {
        return chatLoader.getController();
    }

    public AuthController getAuthController() {
        return authLoader.getController();
    }

    public static ClientChatApplication getInstance() {
        return INSTANCE;
    }

    public String getAuthorizedLogin() {
        return authorizedLogin;
    }

    public void setAuthorizedLogin(String authorizedLogin) {
        this.authorizedLogin = authorizedLogin;
    }

    public static void main(String[] args) {
        launch();
    }
}