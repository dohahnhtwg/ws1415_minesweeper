package de.htwg.se.aview.tui.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class NewAccountResponse implements Serializable {

    private boolean success;

    public NewAccountResponse(boolean success)  {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
