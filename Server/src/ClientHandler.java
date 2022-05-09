import com.gb.clientchat.co.Command;
import com.gb.clientchat.co.CommandType;
import com.gb.clientchat.co.commands.AuthCommandData;
import com.gb.clientchat.co.commands.PrivateMessageCommandData;
import com.gb.clientchat.co.commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {
    private ChatServer chatServer;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String authenticatedLogin;
    private Timer timer;

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
                    closeConnectionAfterTime();
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

    private void closeConnectionAfterTime() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("RUN closeConnectionAfterTime");
                if (getAuthenticatedLogin().isEmpty()) {
                    closeConnection();
                }
                timer.cancel();
            }
        }, 1000 * 120);
    }

    public void authentication() throws IOException, ClassNotFoundException {
        while (true) {
            Command command = readCommand();

            if (command == null) {
                System.err.println("authentication command is null");
                closeConnection();
                return;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData authCommandData = (AuthCommandData) command.getData();
                String login = chatServer.getAuthService().getLoginByLoginPass(authCommandData.getLogin(), authCommandData.getPassword());
                if (login == null) {
                    sendCommand(Command.authErrorCommand("Invalid credentials"));
                } else {
                    if (!chatServer.isNickBusy(login)) {
                        sendCommand(Command.authOkCommand(login));
                        authenticatedLogin = login;
                        chatServer.subscribe(this);
                        return;
                    } else {
                        sendCommand(Command.errorCommand("This client is already authorized"));
                    }
                }
            }
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
        System.out.println("Start read messages");
        while (true) {
            Command command = readCommand();
            if (command == null) {
                closeConnection();
                return;
            }

            System.out.println("Received message on server: " + command.getType());

            if (command.getType() == CommandType.PRIVATE_MESSAGE) {
                PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                chatServer.sendMessageByLogin(data.getReceiver(), data.getMessage(), getAuthenticatedLogin());
            } else if (command.getType() == CommandType.PUBLIC_MESSAGE) {
                PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                chatServer.broadcastMessage(this, data.getMessage());
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