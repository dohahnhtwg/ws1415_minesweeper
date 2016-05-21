package de.htwg.se.aview.tui.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class LoginResponse implements Serializable {

    private boolean success;

    public LoginResponse(boolean success)  {
        this.success = success;
    }

    public boolean isSuccess()  {
        return success;
    }
}
