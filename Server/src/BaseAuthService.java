import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private List<Client> entries;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Client("login1", "pass1", "nick1"));
        entries.add(new Client("login2", "pass2", "nick2"));
        entries.add(new Client("login3", "pass3", "nick3"));
    }

    @Override
    public String getLoginByLoginPass(String login, String pass) {
        for (Client client : entries) {
            if (client.getLogin().equals(login) && client.getPassword().equals(pass)) return client.getLogin();
        }
        return null;
    }
}
