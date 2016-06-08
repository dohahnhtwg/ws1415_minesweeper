package de.htwg.se.controller.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import de.htwg.se.controller.messages.FieldController.FieldRequest;
import de.htwg.se.controller.messages.MainController.FieldResponse;
import de.htwg.se.model.impl.Field;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by dohahn on 08.06.2016.
 */
public class FieldControllerTest {

    private static ActorSystem system;

    @BeforeClass
    public static void setup()  {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown()    {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void fieldRequestShouldBeAnsweredWithFielResponse()   {
        new JavaTestKit(system) {{
            final Props props = Props.create(FieldController.class);
            final ActorRef subject = system.actorOf(props);

            FieldRequest request = new FieldRequest();
            subject.tell(request, getRef());
            FieldResponse f = expectMsgClass(FieldResponse.class);
        }};
    }
}
