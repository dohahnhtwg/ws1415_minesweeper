/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.controller.impl;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.aview.messages.*;
import de.htwg.se.controller.messages.*;
import de.htwg.se.controller.messages.FieldController.CreateRequest;
import de.htwg.se.controller.messages.FieldController.FieldRequest;
import de.htwg.se.controller.messages.FieldController.NewFieldMessage;
import de.htwg.se.controller.messages.FieldController.RestartRequest;
import de.htwg.se.controller.messages.MainController.*;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;
import de.htwg.se.net.ApacheHttpClientPost;
import de.htwg.se.net.Highscore;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.HashSet;
import java.util.Set;


@Singleton
public class MainController extends UntypedActor {

    private final ApacheHttpClientPost restClient;
    private ActorRef fieldController;
    private IUser user;
    private IStatistic statistic;
    private boolean isStarted = false;
    private DataAccessObject database;
    private Long elapsedTime = 0L;
    private final Set<ActorRef> subscribers = new HashSet<>();


    @Inject
    public MainController(DataAccessObject database)  {
        this.database = database;
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        this.fieldController = getContext().actorOf(Props.create(FieldController.class), "fieldController");
        fieldController.tell(new NewFieldMessage(this.user.getPlayingField()), self());
        this.statistic = this.user.getStatistic();
        this.restClient = new ApacheHttpClientPost();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof FinishGameMessage) {
            finishGame();
            return;
        }
        if(message instanceof LoginRequest) {
            logIn((LoginRequest)message);
            return;
        }
        if(message instanceof NewAccountRequest) {
            addNewAccount((NewAccountRequest)message);
            return;
        }
        if(message instanceof NewGameRequest)   {
            create();
            return;
        }
        if(message instanceof NewSizeRequest)   {
            NewSizeRequest msg = (NewSizeRequest)message;
            create(msg.getLines(), msg.getColumns(), msg.getMines());
            return;
        }
        if(message instanceof RedoRequest)  {
            redo((RedoRequest)message);
            return;
        }
        if(message instanceof UndoRequest)  {
            undo((UndoRequest)message);
            return;
        }
        if(message instanceof RevealCellRequest) {
            revealCell((RevealCellRequest)message);
            return;
        }
        if(message instanceof StatisticRequest) {
            for (ActorRef target: subscribers) {
                target.tell(new StatisticResponse(statistic), self());
            }
            return;
        }
        if (message instanceof RegisterRequest) {
            subscribers.add(getSender());
            fieldController.tell(new FieldRequest(getSender()), self());
        }
        if(message instanceof FieldResponse) {
            FieldResponse response = (FieldResponse)message;
            if (response.getTarget() == null) {
                for (ActorRef target: subscribers) {
                    target.tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
                }
            } else {
                response.getTarget().tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
            }
        }
        if(message instanceof RevealCellResponse)  {
            handleRevealCellResponse((RevealCellResponse)message);
            return;
        }
        if(message instanceof TimeRequest) {
            sender().tell(new TimeResponse(getCurrentTime()), self());
            return;
        }
        unhandled(message);
    }

    private void finishGame() {
        database.update(user);
    }

    private void logIn(LoginRequest msg) {
        IUser userFromDb = database.read(msg.getUsername());
        if(userFromDb == null) {
            sender().tell(new LoginResponse(false), self());
        } else {
            user = userFromDb;
            fieldController.tell(new NewFieldMessage(userFromDb.getPlayingField()), self());
            statistic = userFromDb.getStatistic();
            for (ActorRef target: subscribers) {
                target.tell(new LoginResponse(true), self());
            }
            fieldController.tell(new FieldRequest(), self());
        }
    }

    private void addNewAccount(NewAccountRequest msg) {
        if (msg.getUsername().isEmpty() || msg.getPassword().isEmpty()) {
            sender().tell(new NewAccountResponse(false), self());
        } else {
            Timeout timeout = new Timeout(Duration.create(5, "seconds"));
            Future<Object> future = Patterns.ask(fieldController, new CreateRequest(9, 9, 10), timeout);
            try {
                FieldResponse result = (FieldResponse) Await.result(future, timeout.duration());
                IUser userForDb = new User(msg.getUsername(), msg.getPassword(), result.getField());
                if(database.contains(userForDb)) {
                    for (ActorRef target: subscribers) {
                        target.tell(new NewAccountResponse(false), self());
                    }
                } else {
                    database.create(userForDb);
                    for (ActorRef target: subscribers) {
                        target.tell(new NewAccountResponse(true), self());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void create() {
        fieldController.tell(new RestartRequest(), self());
    }

    private void create(int lines, int columns, int nMines) {
        fieldController.tell(new CreateRequest(lines, columns, nMines), self());
    }

    private void undo(UndoRequest msg) {
        fieldController.tell(msg, self());
    }

    private void redo(RedoRequest msg) {
        fieldController.tell(msg, self());
    }

    private void revealCell(RevealCellRequest msg) {
        if (!isStarted) {
            startTimer();
        }
        fieldController.tell(msg, self());
    }

    private void handleRevealCellResponse(RevealCellResponse response) {
        if(response.getField().isGameOver() || response.getField().isVictory()) {
            stopTimer();
            statistic.updateStatistic(response.getField().isVictory(), elapsedTime);
            if (statistic.getMinTime() < Long.MAX_VALUE && response.getField().isVictory()) {

                Highscore highscore = new Highscore(
                        "Minesweeper",
                        user.getName(),
                        String.valueOf((1000 - statistic.getMinTime() / 1000) * response.getField().getnMines())
                );
                restClient.post(highscore);
            }
        }
        for (ActorRef target: subscribers) {
            target.tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
        }
    }

    private void startTimer() {
        isStarted = true;
        elapsedTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        isStarted = false;
        elapsedTime = System.currentTimeMillis() - elapsedTime;
    }

    private Long getCurrentTime() {
        if (isStarted) {
            return (System.currentTimeMillis() - elapsedTime) / 1000;
        }
        return elapsedTime / 1000;
    }
}
