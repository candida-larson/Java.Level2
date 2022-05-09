module com.gb.clientchat.clientchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires Commands;

    opens com.gb.clientchat.clientchat to javafx.fxml;
    exports com.gb.clientchat.clientchat;
    exports com.gb.clientchat.clientchat.model;
    opens com.gb.clientchat.clientchat.model to javafx.fxml;
    exports com.gb.clientchat.clientchat.controllers;
    opens com.gb.clientchat.clientchat.controllers to javafx.fxml;
}