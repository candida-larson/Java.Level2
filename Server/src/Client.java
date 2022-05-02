public class Client {

    private final String login;
    private final String password;
    private final String fullname;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public Client(String login, String password, String nick) {
        this.login = login;
        this.password = password;
        this.fullname = nick;
    }
}
