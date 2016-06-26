package de.htwg.se.controller.messages.FieldController;

import akka.actor.ActorRef;

import java.io.Serializable;

/**
 * Created by dohahn on 24.05.2016.
 * Request for the actual field
 * On receive the FieldController will answer with a FieldResponse
 */
public class FieldRequest implements Serializable {
    private ActorRef target;
    public FieldRequest(){}
    public FieldRequest(ActorRef target){
        this.target = target;
    }

    public ActorRef getTarget() {
        return target;
    }
}
