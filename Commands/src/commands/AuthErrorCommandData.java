package commands;

import java.io.Serializable;

public class AuthErrorCommandData implements Serializable {
    private String message;

    public AuthErrorCommandData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
