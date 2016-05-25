package de.htwg.se.aview.messages;

import java.io.Serializable;

/**
 * Created by dohahn on 25.05.2016.
 */
public class TimeResponse implements Serializable {

    private Long currentTime;

    public TimeResponse(Long currentTime)   {
        this.currentTime = currentTime;
    }

    public Long getCurrentTime()    {
        return currentTime;
    }
}
