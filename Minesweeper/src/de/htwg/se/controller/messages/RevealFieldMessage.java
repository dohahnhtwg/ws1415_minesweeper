package de.htwg.se.controller.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class RevealFieldMessage implements Serializable {
    private Integer x;
    private Integer y;

    public RevealFieldMessage(Integer x, Integer y)  {
        this.x = x;
        this.y = y;
    }

    public Integer getX()   {
        return x;
    }

    public Integer getY()  {
        return y;
    }
}
