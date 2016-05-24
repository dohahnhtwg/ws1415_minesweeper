package de.htwg.se.controller.messages;

import de.htwg.se.model.IField;

import java.io.Serializable;

/**
 * Created by GAAB on 24.05.2016.
 */
public class NewFieldMessage implements Serializable {

    private IField field;

    public NewFieldMessage(IField field)    {
        this.field = field;
    }

    public IField getField()    {
        return field;
    }

    public void set(IField field)   {
        this.field = field;
    }
}
