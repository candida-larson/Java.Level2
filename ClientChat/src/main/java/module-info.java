module com.gb.clientchat.clientchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gb.clientchat.clientchat to javafx.fxml;
    exports com.gb.clientchat.clientchat;
}