package de.htwg.se.controller.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import de.htwg.se.controller.messages.FieldController.CreateRequest;
import de.htwg.se.controller.messages.FieldController.FieldRequest;
import de.htwg.se.controller.messages.FieldController.RestartRequest;
import de.htwg.se.controller.messages.MainController.FieldResponse;
import de.htwg.se.controller.messages.RedoRequest;
import de.htwg.se.controller.messages.RevealCellRequest;
import de.htwg.se.controller.messages.RevealCellResponse;
import de.htwg.se.controller.messages.UndoRequest;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dohahn on 08.06.2016.
 * FieldControllerTest
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
            expectMsgClass(FieldResponse.class);
        }};
    }

    @Test
    public void restartRequestShouldRestartGameAndAnswerWithFieldResponse() {
        new JavaTestKit(system) {{
            final Props props = Props.create(FieldController.class);
            final ActorRef subject = system.actorOf(props);

            RestartRequest request = new RestartRequest();
            subject.tell(request, getRef());
            FieldResponse response = expectMsgClass(FieldResponse.class);
            IField field = response.getField();

            assertFalse(field.isGameOver());
            assertFalse(field.isVictory());
            assertEquals(9, field.getLines());
            assertEquals(9, field.getColumns());
            assertEquals(10, field.getnMines());

            ICell[][] playingField = field.getPlayingField();
            for(int i = 1; i < playingField.length; i++)    {
                for (int j = 1; j < playingField[i].length; j++)    {
                    assertFalse(playingField[i][j].isRevealed());
                }
            }
        }};
    }

    @Test
    public void createRequestShouldBeAnsweredWithNewCreatedField()  {
        new JavaTestKit(system) {{
            final Props props = Props.create(FieldController.class);
            final ActorRef subject = system.actorOf(props);

            CreateRequest request = new CreateRequest(2, 2, 2);
            subject.tell(request, getRef());
            FieldResponse response = expectMsgClass(FieldResponse.class);
            IField field = response.getField();

            assertFalse(field.isGameOver());
            assertFalse(field.isVictory());
            assertEquals(2, field.getLines());
            assertEquals(2, field.getColumns());
            assertEquals(2, field.getnMines());

            ICell[][] playingField = field.getPlayingField();
            int mines = 0;
            for(int i = 1; i < playingField.length; i++)    {
                for (int j = 1; j < playingField[i].length; j++)    {
                    assertFalse(playingField[i][j].isRevealed());
                    if(playingField[i][j].getValue() == -1) {
                        mines++;
                    }
                }
            }
            assertEquals(2, mines);
        }};
    }

    @Test
    public void unAndRedoShouldUnRedoLastMoveAndAnswerWithFieldResponse()   {
        new JavaTestKit(system) {{
            final Props props = Props.create(FieldController.class);
            final ActorRef subject = system.actorOf(props);

            CreateRequest createRequest = new CreateRequest(4, 4, 1);
            subject.tell(createRequest, getRef());
            expectMsgClass(FieldResponse.class);

            RevealCellRequest revealRequest = new RevealCellRequest(1,1);
            subject.tell(revealRequest, getRef());
            RevealCellResponse revealResponse = expectMsgClass(RevealCellResponse.class);
            IField fieldBeforeUndo = revealResponse.getField();
            assertFalse(!fieldBeforeUndo.getPlayingField()[1][1].isRevealed());

            UndoRequest undoRequest = new UndoRequest();
            subject.tell(undoRequest, getRef());
            FieldResponse responseAfterUndo = expectMsgClass(FieldResponse.class);
            IField fieldAfterUndo = responseAfterUndo.getField();
            assertFalse(fieldAfterUndo.getPlayingField()[1][1].isRevealed());

            RedoRequest redoRequest = new RedoRequest();
            subject.tell(redoRequest, getRef());
            FieldResponse responseAfterRedo = expectMsgClass(FieldResponse.class);
            IField fieldAfterRedo = responseAfterRedo.getField();
            assertEquals(fieldAfterRedo, fieldAfterUndo);
        }};
    }

    @Test
    public void ifGameIsOverRevealFieldShouldDoNothingAndAnswerWithFieldResonse()  {
        new JavaTestKit(system) {{
            final Props props = Props.create(FieldController.class);
            final ActorRef subject = system.actorOf(props);

            CreateRequest createRequest = new CreateRequest(2, 2, 4);
            subject.tell(createRequest, getRef());
            expectMsgClass(FieldResponse.class);

            RevealCellRequest revealRequest = new RevealCellRequest(1,1);
            subject.tell(revealRequest, getRef());
            RevealCellResponse revealResponse = expectMsgClass(RevealCellResponse.class);
            System.out.println(revealResponse.getField().toString());
            assertTrue(revealResponse.getField().isGameOver());

            revealRequest = new RevealCellRequest(0,1);
            subject.tell(revealRequest, getRef());
            revealResponse = expectMsgClass(RevealCellResponse.class);
            assertFalse(revealResponse.getField().getPlayingField()[0][1].isRevealed());
        }};
    }
}
