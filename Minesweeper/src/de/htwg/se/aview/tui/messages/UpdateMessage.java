package de.htwg.se.aview.tui.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class UpdateMessage implements Serializable {

    private String field;
    private boolean isGameOver;
    private boolean isVictory;
    private Long currentTime;

    public UpdateMessage(String field, boolean isGameOver, boolean isVictory, Long currentTime)   {
        this.isGameOver = isGameOver;
        this.isVictory = isVictory;
        this.currentTime = currentTime;
        this.field = field;
    }

    public String getField()    {
        return  field;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isVictory()  {
        return isVictory;
    }

    public Long getCurrentTime()    {
        return currentTime;
    }
}
