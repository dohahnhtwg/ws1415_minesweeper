package de.htwg.se.aview.tui.messages;

import de.htwg.se.model.IField;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class UpdateMessage implements Serializable {

    private IField field;
    private Long currentTime;

    public UpdateMessage(IField field, Long currentTime)   {
        this.currentTime = currentTime;
        this.field = field;
    }

    public IField getField()    { return  field; }

    public Long getCurrentTime()    {
        return currentTime;
    }
}
