package de.htwg.se.controller.messages.MainController;

import akka.actor.ActorRef;
import de.htwg.se.model.IField;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Response with the actual Field
 * On receive the MainController will send a UpdateMessage to parent
 */
public class FieldResponse implements Serializable {

    /**
     * The actual field
     */
    private IField field;
    private ActorRef target;

    public FieldResponse(IField field)  {
        this.field = field;
    }

    public FieldResponse(IField field, ActorRef target)  {
        this.field = field;
        this.target = target;
    }

    public IField getField()    {
        return field;
    }

    public ActorRef getTarget() {
        return target;
    }
}
