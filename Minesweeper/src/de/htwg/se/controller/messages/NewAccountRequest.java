package de.htwg.se.controller.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class NewAccountRequest implements Serializable {

    private String username;
    private String password;

    public NewAccountRequest(String username, String password)  {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
