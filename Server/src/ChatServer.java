import com.gb.clientchat.co.Command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private final int PORT = 8187;
    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public ChatServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void notifyUserListUpdated() {
        List<String> users = new ArrayList<>();
        for (ClientHandler client : clients) {
            users.add(client.getAuthenticatedLogin());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUserListCommand(users));
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getAuthenticatedLogin().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMessage(ClientHandler sender, String message) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler != sender) {
                clientHandler.sendCommand(Command.clientMessageCommand(sender.getAuthenticatedLogin(), message));
            }
        }
    }

    public synchronized void sendMessageByLogin(String login, String message, String sender) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getAuthenticatedLogin().equals(login)) {
                clientHandler.sendCommand(Command.clientMessageCommand(sender, message));
                break;
            }
        }
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }
}
