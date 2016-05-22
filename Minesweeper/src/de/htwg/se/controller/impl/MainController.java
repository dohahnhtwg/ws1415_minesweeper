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

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.undo.UndoManager;

import akka.actor.UntypedActor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.aview.tui.messages.LoginResponse;
import de.htwg.se.aview.tui.messages.NewAccountResponse;
import de.htwg.se.aview.tui.messages.PrintStatisticMessage;
import de.htwg.se.aview.tui.messages.UpdateMessage;
import de.htwg.se.controller.IMainController;
import de.htwg.se.controller.RevealFieldCommand;
import de.htwg.se.controller.messages.*;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.database.db4o.db4oDatabase;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;


@Singleton
public class MainController extends UntypedActor implements IMainController {

    private FieldController fieldController;
    private IUser user;
    private IStatistic statistic;
    private boolean isStarted = false;
    private DataAccessObject database;
    private Long elapsedTime = 0L;

    @Inject
    public MainController()  {
        //undoManager = new UndoManager();
        this.database = new db4oDatabase();
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        //this.playingField = this.user.getPlayingField();
        this.fieldController = new FieldController();
        this.fieldController.setField(this.user.getPlayingField());
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
            redo();
            return;
        }
        if(message instanceof UndoMessage)  {
            undo();
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
            getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(),
                    fieldController.getField().isVictory(), getCurrentTime()), self());
            return;
        }
        unhandled(message);
    }

    public void finishGame() {
        database.update(user);
    }

    public void logIn(LoginRequest msg) {
        IUser userFromDb = database.read(msg.getUsername());
        if(userFromDb == null)    {
            getContext().parent().tell(new LoginResponse(false), self());
        } else {
            user = userFromDb;
            fieldController.setField(userFromDb.getPlayingField());
            statistic = userFromDb.getStatistic();
            getContext().parent().tell(new LoginResponse(true), self());
            getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
        }
    }

    public void addNewAccount(NewAccountRequest msg) {
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
        fieldController.restart();
        getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
    }

    public void create(int lines, int columns, int nMines) {
        fieldController.create(lines, columns, nMines);
        getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
    }

    public void undo() {
        fieldController.undo();
        getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
    }

    public void redo() {
        fieldController.redo();
        getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
    }

    private void revealField(RevealFieldMessage msg) {
        if(fieldController.getField().isGameOver() || fieldController.getField().isVictory()) {
            return;
        }
        if (!isStarted) {
            startTimer();
        }
        fieldController.revealField(msg);
        if(fieldController.getField().isGameOver()) {
            stopTimer();
            statistic.updateStatistic(false, elapsedTime);
        } else {
            if(fieldController.getField().isVictory())  {
                stopTimer();
                statistic.updateStatistic(true, elapsedTime);
            }
        }
        getContext().parent().tell(new UpdateMessage(getField(), fieldController.getField().isGameOver(), fieldController.getField().isVictory(), getCurrentTime()), self());
    }

    public String getField()    {
        return fieldController.getField().toString();
    }

    /* No longer needed, use actor */

    @Override
    public boolean isVictory() {
        return fieldController.getField().isVictory();
    }

    @Override
    public boolean isGameOver() {
        return fieldController.getField().isGameOver();
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
    public void revealField(int x, int y)  {

    }

    /* only called in gui and web? */

    public IField getPlayingField()  {
        return fieldController.getField();
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
}
