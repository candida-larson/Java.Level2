package commands;

import java.io.Serializable;

public class AuthOkCommandData implements Serializable {

    private final String login;

    public AuthOkCommandData(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}