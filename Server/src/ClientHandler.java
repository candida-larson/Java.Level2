import commands.AuthCommandData;
import commands.AuthErrorCommandData;
import commands.PrivateMessageCommandData;
import commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private ChatServer chatServer;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String authenticatedLogin;

    public String getAuthenticatedLogin() {
        return authenticatedLogin;
    }

    public ClientHandler(ChatServer chatServer, Socket socket) {
        try {
            this.chatServer = chatServer;
            this.socket = socket;
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.authenticatedLogin = "";
            new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authentication() throws IOException, ClassNotFoundException {
        while (true) {
            Command command = readCommand();

            if (command == null) {
                System.err.println("authentication command is null");
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData authCommandData = (AuthCommandData) command.getData();
                String login = chatServer.getAuthService().getLoginByLoginPass(authCommandData.getLogin(), authCommandData.getPassword());
                if (login == null) {
                    sendCommand(Command.authErrorCommand("Invalid credentials"));
                } else {
                    if (chatServer.isNickBusy(login)) {
                        sendCommand(Command.authOkCommand(login));
                        authenticatedLogin = login;
                        chatServer.subscribe(this);
                        return;
                    } else {
                        sendCommand(Command.errorCommand("This client is already authorized"));
                    }
                }
            }

//            if (message.startsWith("/auth")) {
//                String[] parts = message.split("\\s");
//                String login = chatServer.getAuthService().getLoginByLoginPass(parts[1], parts[2]);
//                if (login != null) {
//                    if (!chatServer.isNickBusy(login)) {
//                        sendMessage("/authok " + login);
//                        authenticatedLogin = login;
//                        chatServer.broadcastMessage("/login " + authenticatedLogin + " зашел в чат");
//                        chatServer.subscribe(this);
//                        return;
//                    } else {
//                        sendMessage("/authbusy " + parts[1]);
//                    }
//                } else {
//                    sendMessage("/autherror " + parts[1]);
//                }
//            }
        }
    }

    private Command readCommand() {
        try {
            return (Command) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("readCommand on server error");
        }
        return null;
    }

    public void readMessages() throws IOException, ClassNotFoundException {
//        while (true) {
//            String message = objectInputStream.readUTF();
//            if (message.startsWith("/w")) {
//                String[] parts = message.split(" ", 3);
//                chatServer.sendMessageByLogin(parts[1], String.format("/mfrom %s %s", getAuthenticatedLogin(), parts[2]));
//            }
//        }

        while (true) {
            Command command = (Command) objectInputStream.readObject();
            if (command.getType() == CommandType.PRIVATE_MESSAGE) {
                PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                chatServer.sendMessageByLogin(data.getReceiver(), data.getMessage());
            } else if (command.getType() == CommandType.PUBLIC_MESSAGE) {
                PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                chatServer.broadcastMessage(data.getMessage());
            }
        }
    }

    public void sendCommand(Command command) {
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            System.err.println("error send command");
        }
    }

    public void closeConnection() {
        chatServer.unsubscribe(this);
        // chatServer.broadcastMessage(authenticatedLogin + " вышел из чата");
        try {
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.close();
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