package de.htwg.se.aview.tui.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 * Message with tui input
 */
public class InputMessage implements Serializable {
    private String input;

    public InputMessage(String input)  {
        this.input = input;
    }

    public String getInput()    {
        return  input;
    }
}
