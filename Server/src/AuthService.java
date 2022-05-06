public interface AuthService {
    void start();

    String getLoginByLoginPass(String login, String pass);

    void stop();
}

