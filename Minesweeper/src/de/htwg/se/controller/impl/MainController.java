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
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.aview.messages.*;
import de.htwg.se.controller.IMainController;
import de.htwg.se.controller.messages.*;
import de.htwg.se.controller.messages.FieldController.CreateRequest;
import de.htwg.se.controller.messages.FieldController.FieldRequest;
import de.htwg.se.controller.messages.FieldController.NewFieldMessage;
import de.htwg.se.controller.messages.FieldController.RestartRequest;
import de.htwg.se.controller.messages.MainController.*;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.database.db4o.db4oDatabase;
import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;


@Singleton
public class MainController extends UntypedActor implements IMainController {

    private ActorRef fieldController;
    private IUser user;
    private IStatistic statistic;
    private boolean isStarted = false;
    private DataAccessObject database;
    private Long elapsedTime = 0L;

    @Inject
    public MainController(DataAccessObject database)  {
        this.database = database;
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
            System.out.println(this.user.getPlayingField().toString());
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        this.fieldController = getContext().actorOf(Props.create(FieldController.class), "fieldController");
        fieldController.tell(new NewFieldMessage(this.user.getPlayingField()), self());
        this.statistic = this.user.getStatistic();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof FinishGameMessage)    {
            finishGame();
            return;
        }
        if(message instanceof LoginRequest) {
            logIn((LoginRequest)message);
            return;
        }
        if(message instanceof NewAccountRequest)    {
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
        if(message instanceof RevealCellRequest)    {
            revealCell((RevealCellRequest)message);
            return;
        }
        if(message instanceof StatisticRequest) {
            getContext().parent().tell(new StatisticResponse(statistic), self());
            return;
        }
        if(message instanceof UpdateRequest)    {
            fieldController.tell(new FieldRequest(), self());
            return;
        }
        if(message instanceof FieldResponse)    {
            FieldResponse response = (FieldResponse)message;
            getContext().parent().tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
            return;
        }
        if(message instanceof RevealCellResponse)  {
            handleRevealCellResponse((RevealCellResponse)message);
            return;
        }
        if(message instanceof TimeResponse) {
            getContext().parent().tell(new TimeResponse(getCurrentTime()), self());
            return;
        }
        unhandled(message);
    }

    //TODO make private
    public void finishGame() {
        database.update(user);
    }

    private void logIn(LoginRequest msg) {
        IUser userFromDb = database.read(msg.getUsername());
        if(userFromDb == null)    {
            getContext().parent().tell(new LoginResponse(false), self());
        } else {
            user = userFromDb;
            fieldController.tell(new NewFieldMessage(userFromDb.getPlayingField()), self());
            statistic = userFromDb.getStatistic();
            getContext().parent().tell(new LoginResponse(true), self());
            fieldController.tell(new FieldRequest(), self());
        }
    }

    private void addNewAccount(NewAccountRequest msg) {
        if (msg.getUsername().isEmpty() || msg.getPassword().isEmpty()) {
            getContext().parent().tell(new NewAccountResponse(false), self());
        } else {
            IUser userForDb = new User(msg.getUsername(), msg.getPassword());
            if(database.contains(userForDb)) {
                getContext().parent().tell(new NewAccountResponse(false), self());
            } else {
                database.create(userForDb);
                getContext().parent().tell(new NewAccountResponse(true), self());
            }
        }
    }

    //TODO make private after gui-actor rework
    public void create() {
        fieldController.tell(new RestartRequest(), self());
    }

    //TODO make private after gui-actor rework
    public void create(int lines, int columns, int nMines) {
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
        if(response.getField().isGameOver()) {
            stopTimer();
            statistic.updateStatistic(false, elapsedTime);
        } else {
            if(response.getField().isVictory())  {
                stopTimer();
                statistic.updateStatistic(true, elapsedTime);
            }
        }
        getContext().parent().tell(new UpdateMessage(response.getField(), getCurrentTime()), self());
    }

    private void startTimer() {
        isStarted = true;
        elapsedTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        isStarted = false;
        elapsedTime = System.currentTimeMillis() - elapsedTime;
    }

    //TODO make private after gui-actor rework
    public Long getCurrentTime() {
        if (isStarted) {
            return (System.currentTimeMillis() - elapsedTime) / 1000;
        }
        return elapsedTime / 1000;
    }

    /* No longer needed, use actor */

    @Deprecated
    public IField getPlayingField()  {
        return null;
    }

    @Deprecated
    public boolean isVictory() {
        return false;
    }

    @Deprecated
    public boolean isGameOver() {
        return false;
    }

    @Deprecated
    public boolean addNewAccount(String name, String pass) {
        return false;
    }

    @Deprecated
    public boolean logIn(String name, String pass) {
        return false;
    }

    @Deprecated
    public void revealField(int x, int y)  { }

    @Deprecated
    public void undo() { }

    @Deprecated
    public void redo() { }

    @Deprecated
    public IStatistic getUserStatistic() {
        return statistic;
    }

}
