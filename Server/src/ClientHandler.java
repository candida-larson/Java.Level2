import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private ChatServer chatServer;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String authenticatedLogin;

    public String getAuthenticatedLogin() {
        return authenticatedLogin;
    }

    public ClientHandler(ChatServer chatServer, Socket socket) {
        try {
            this.chatServer = chatServer;
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.authenticatedLogin = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException {
        while (true) {
            String message = dataInputStream.readUTF();
            if (message.startsWith("/auth")) {
                String[] parts = message.split("\\s");
                String login = chatServer.getAuthService().getLoginByLoginPass(parts[1], parts[2]);
                if (login != null) {
                    if (!chatServer.isNickBusy(login)) {
                        sendMessage("/authok " + login);
                        authenticatedLogin = login;
                        chatServer.broadcastMessage("/login " + authenticatedLogin + " зашел в чат");
                        chatServer.subscribe(this);
                        return;
                    } else {
                        sendMessage("/authbusy " + parts[1]);
                    }
                } else {
                    sendMessage("/autherror " + parts[1]);
                }
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String message = dataInputStream.readUTF();
            if (message.startsWith("/w")){
                String[] parts = message.split(" ", 3);
                chatServer.sendMessageByLogin(parts[1], String.format("/mfrom %s %s", getAuthenticatedLogin(), parts[2]));
            }

//            System.out.println("от " + authenticatedLogin + ": " + strFromClient);
//            if (strFromClient.equals("/end")) {
//                return;
//            }
//            chatServer.broadcastMessage(authenticatedLogin + ": " + strFromClient);
        }
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        chatServer.unsubscribe(this);
        chatServer.broadcastMessage(authenticatedLogin + " вышел из чата");
        try {
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}