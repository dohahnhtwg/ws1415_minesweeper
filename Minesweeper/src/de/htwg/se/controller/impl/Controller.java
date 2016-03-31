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

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.controller.IController;
import de.htwg.se.controller.RevealFieldCommand;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;
import de.htwg.se.util.observer.Observable;


@Singleton
public class Controller extends Observable implements IController {

    private IField playingField;
    private IUser user;
    private boolean gameOver = false;
    private boolean victory = false;
    private UndoManager undoManager;
    private int victories = 0;
    private int loses = 0;
    private DataAccessObject database;

    public int getVictories() {
        return victories;
    }

    public int getLoses() {
        return loses;
    }

    @Inject
    public Controller(IField playingfield, DataAccessObject database)  {
        undoManager = new UndoManager();
        this.database = database;
        if (database.contains(new User("Default", "Default"))) {
            this.user = database.read("Default", "Default");
        } else {
            this.user = new User("Default", "Default");
            database.create(this.user);
        }
        this.playingField = this.user.getPlayingField();
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void revealField(int x, int y) {
        if(gameOver || isVictory()) {
            return;
        }
        if(playingField.getField()[x][y].getValue() == -1) {
            playingField.getField()[x][y].setRevealed(true);
            gameOver = true;
            loses++;
        } else {
            List<ICell> revelalFieldCommandList = new LinkedList<>();
            revealFieldHelp(x, y, revelalFieldCommandList);
            victory = checkVictory();
            undoManager.addEdit(new RevealFieldCommand(revelalFieldCommandList));
        }
        notifyObservers();
    }

    private void revealFieldHelp(int x, int y, List<ICell> revelalFieldCommandList)  {
        playingField.getField()[x][y].setRevealed(true);
        ((LinkedList<ICell>) revelalFieldCommandList).push(playingField.getField()[x][y]);
        if(playingField.getField()[x][y].getValue() <= 0)  {
            List<Point> fieldsaround = getFieldsAround(x, y);
            for(Point field : fieldsaround) {
                if(checkCellInField(field) && !playingField.getField()[field.x][field.y].isRevealed()) {
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
                if(playingField.getField()[i][j].isRevealed()) {
                    current++;
                }
            }
        }

        if(current == requirement)  {
            victories++;
            return true;
        }
        return false; 
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
        notifyObservers();
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
        notifyObservers();
    }

    public String getField()    {
        return playingField.toString();
    }

    public IField getPlayingField()  {
        return playingField;
    }
    
    public void create() {
        create(playingField.getLines(), playingField.getColumns(), playingField.getnMines());
    }

    public void create(int lines, int columns, int nMines) {
        gameOver = false;
        victory = false;
        playingField.create(lines, columns, nMines);
        notifyObservers();
    }

    @Override
    public void finishGame() {
        database.update(user);
    }

    public boolean addNewAccount(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        IUser userForDb = new User(username, password);
        if(database.contains(userForDb)) {
            return false;
        }
        database.create(userForDb);
        return true;
    }

    public boolean logIn(String username, String password) {
        IUser userFromDb = database.read(username, password);
        if(userFromDb == null)    {
            return false;
        }
        user = userFromDb;
        playingField = userFromDb.getPlayingField();
        notifyObservers();
        return true;
    }

    public IStatistic getUserStatistic() {
        return user.getStatistic();
    }
}
