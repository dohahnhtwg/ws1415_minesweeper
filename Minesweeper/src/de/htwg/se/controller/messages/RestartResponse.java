package de.htwg.se.controller.messages;

import de.htwg.se.model.IField;

import java.io.Serializable;

/**
 * Created by GAAB on 24.05.2016.
 */
public class RestartResponse implements Serializable {

    private IField field;

    public RestartResponse(IField field)    {
        this.field = field;
    }

    public IField getField()    {
        return field;
    }
}
