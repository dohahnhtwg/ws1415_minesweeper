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
import java.util.Iterator;
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
import de.htwg.se.controller.IController;
import de.htwg.se.controller.RevealFieldCommand;
import de.htwg.se.controller.messages.*;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.database.db4o.db4oDatabase;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;


@Singleton
public class Controller extends UntypedActor implements IController {

    private IField playingField;
    private IUser user;
    private IStatistic statistic;
    private boolean gameOver = false;
    private boolean victory = false;
    private boolean isStarted = false;
    private UndoManager undoManager;
    private DataAccessObject database;
    private Long elapsedTime = 0L;

    @Inject
    public Controller()  {
        undoManager = new UndoManager();
        this.database = new db4oDatabase();
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default");
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        this.playingField = this.user.getPlayingField();
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
            playingField = userFromDb.getPlayingField();
            statistic = userFromDb.getStatistic();
            getContext().parent().tell(new LoginResponse(true), self());
            getContext().parent().tell(new UpdateMessage(getField(), gameOver, victory, getCurrentTime()), self());
            //notifyObservers();
            //return true;
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
        create(playingField.getLines(), playingField.getColumns(), playingField.getnMines());
    }

    public void create(int lines, int columns, int nMines) {
        gameOver = false;
        victory = false;
        playingField.create(lines, columns, nMines);
        getContext().parent().tell(new UpdateMessage(getField(), gameOver, victory, getCurrentTime()), self());
        //notifyObservers();
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
        getContext().parent().tell(new UpdateMessage(getField(), gameOver, victory, getCurrentTime()), self());
        //notifyObservers();
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
        getContext().parent().tell(new UpdateMessage(getField(), gameOver, victory, getCurrentTime()), self());
        //notifyObservers();
    }

    private void revealField(RevealFieldMessage msg) {
        if(gameOver || victory) {
            return;
        }
        if (!isStarted) {
            startTimer();
        }
        if(playingField.getField()[msg.getX()][msg.getY()].getValue() == -1) {
            playingField.getField()[msg.getX()][msg.getY()].setIsRevealed(true);
            gameOver = true;
            stopTimer();
            statistic.updateStatistic(false, elapsedTime);
        } else {
            List<ICell> revelalFieldCommandList = new LinkedList<>();
            revealFieldHelp(msg.getX(), msg.getY(), revelalFieldCommandList);
            victory = checkVictory();
            if (victory) {
                stopTimer();
                statistic.updateStatistic(true, elapsedTime);
            }
            undoManager.addEdit(new RevealFieldCommand(revelalFieldCommandList));
        }
        getContext().parent().tell(new UpdateMessage(getField(), gameOver, victory, getCurrentTime()), self());
        //notifyObservers();
    }

    private void revealFieldHelp(int x, int y, List<ICell> revelalFieldCommandList)  {
        playingField.getField()[x][y].setIsRevealed(true);
        ((LinkedList<ICell>) revelalFieldCommandList).push(playingField.getField()[x][y]);
        if(playingField.getField()[x][y].getValue() <= 0)  {
            List<Point> fieldsaround = getFieldsAround(x, y);
            for(Point field : fieldsaround) {
                if(checkCellInField(field) && !playingField.getField()[field.x][field.y].getIsRevealed()) {
                    revealFieldHelp(field.x, field.y, revelalFieldCommandList);
                }
            }
        }
    }

    private List<Point> getFieldsAround(int x, int y) {
        List<Point> fieldsAround = new ArrayList<>();
        fieldsAround.add(new Point(x - 1, y));
        fieldsAround.add(new Point(x + 1, y));
        fieldsAround.add(new Point(x - 1, y - 1));
        fieldsAround.add(new Point(x - 1, y + 1));
        fieldsAround.add(new Point(x + 1, y + 1));
        fieldsAround.add(new Point(x + 1, y - 1));
        fieldsAround.add(new Point(x , y - 1));
        fieldsAround.add(new Point(x , y + 1));
        return fieldsAround;
    }

    private boolean checkCellInField(Point cell)    {
        return (cell.getX() > 0 && cell.getY() > 0) && (cell.getX() < playingField.getField().length - 1 && cell.getY() < playingField.getField()[(int) cell.getX()].length - 1);
    }

    private boolean checkVictory()  {
        int requirement = playingField.getLines() * playingField.getColumns() - playingField.getnMines();
        int current = 0;

        for (int i = 0; i < playingField.getField().length; i++)   {
            for (int j = 0; j < playingField.getField()[0].length; j ++)    {
                if(playingField.getField()[i][j].getIsRevealed()) {
                    current++;
                }
            }
        }

        return current == requirement;
    }

    public String getField()    {
        return playingField.toString();
    }

    /* called in gui? */

    @Override
    public boolean isVictory() {
        return victory;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
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

    public IField getPlayingField()  {
        return playingField;
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

    private static final int INITIAL_CAPACITY = 2;

    private List<IObserver> subscribers = new ArrayList<IObserver>(INITIAL_CAPACITY);

    public void addObserver(IObserver s) {
        subscribers.add(s);
    }

    public void removeObserver(IObserver s) {
        subscribers.remove(s);
    }

    public void removeAllObservers() {
        subscribers.clear();

    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Event e) {
        for (Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();) {
            IObserver observer = iter.next();
            observer.update(e);
        }
    }
}
