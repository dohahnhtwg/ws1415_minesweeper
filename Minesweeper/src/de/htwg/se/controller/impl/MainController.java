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

import de.htwg.se.aview.tui.messages.LoginResponse;
import de.htwg.se.aview.tui.messages.NewAccountResponse;
import de.htwg.se.aview.tui.messages.PrintStatisticMessage;
import de.htwg.se.aview.tui.messages.UpdateMessage;
import de.htwg.se.controller.IMainController;
import de.htwg.se.controller.messages.*;
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
    public MainController()  {
        this.database = new db4oDatabase();
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
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
        if(message instanceof NewGameMessage)   {
            create();
            return;
        }
        if(message instanceof NewSizeMessage)   {
            NewSizeMessage msg = (NewSizeMessage)message;
            create(msg.getLines(), msg.getColumns(), msg.getMines());
            return;
        }
        if(message instanceof RedoMessage)  {
            redo((RedoMessage)message);
            return;
        }
        if(message instanceof UndoMessage)  {
            undo((UndoMessage)message);
            return;
        }
        if(message instanceof RevealFieldMessage)    {
            revealField((RevealFieldMessage)message);
            return;
        }
        if(message instanceof StatisticRequest) {
            getContext().parent().tell(new PrintStatisticMessage(statistic), self());
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
        if(message instanceof RevealFieldResponse)  {
            handleRevealFieldResponse((RevealFieldResponse)message);
            return;
        }
        unhandled(message);
    }

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

    public void create() {
        fieldController.tell(new RestartRequest(), self());
    }

    public void create(int lines, int columns, int nMines) {
        fieldController.tell(new CreateRequest(lines, columns, nMines), self());
    }

    private void undo(UndoMessage msg) {
        fieldController.tell(msg, self());
    }

    private void redo(RedoMessage msg) {
        fieldController.tell(msg, self());
    }

    private void revealField(RevealFieldMessage msg) {
        if (!isStarted) {
            startTimer();
        }
        fieldController.tell(msg, self());
    }

    private void handleRevealFieldResponse(RevealFieldResponse response) {
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


    /* only called in gui and web? Refactor with use of actors */

    @Override
    public String getField() {
        return null;
    }

    public IField getPlayingField()  {
        return null;
    }

    public IStatistic getUserStatistic() {
        return statistic;
    }

    public void startTimer() {
        isStarted = true;
        elapsedTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        isStarted = false;
        elapsedTime = System.currentTimeMillis() - elapsedTime;
    }

    public Long getCurrentTime() {
        if (isStarted) {
            return (System.currentTimeMillis() - elapsedTime) / 1000;
        }
        return elapsedTime / 1000;
    }

    public boolean isStarted() {
        return isStarted;
    }

    /* No longer needed, use actor */

    @Override
    public boolean isVictory() {
        return false;
    }

    @Override
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
}
