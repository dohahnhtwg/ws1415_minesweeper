package de.htwg.se.controller.impl;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by dohahn on 12.06.2016.
 */
public class MainControllerTest {
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
    public void SomeTest()   {

    }
}
