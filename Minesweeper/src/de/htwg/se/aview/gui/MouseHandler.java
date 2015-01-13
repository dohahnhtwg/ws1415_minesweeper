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

package de.htwg.se.aview.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeMap;

public final class MouseHandler extends MouseAdapter {

    private int x, y;
    private PlayingFieldPanel field;

    private int index = Constances.ZERO;

    public MouseHandler(final int x, final int y, final PlayingFieldPanel panel)
    {
        this.x = x;
        this.y = y;
        field = panel;
    }

    @Override
    public void mouseClicked(final MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON3 && field.getButtons()[x][y].isEnabled()) {
            index = ++index % Constances.BUTTONTEXT.length;
            field.getButtons()[x][y].setText(Constances.BUTTONTEXT[index]);
            if (!field.getMarked().containsKey(x)) {
                field.getMarked().put(x, new TreeMap<Integer, String>());
            } 
//            field.getMarked().get(x).put(y, Constances.BUTTONTEXT[index]);
            field.getMarked().get(x).put(y, Constances.BUTTONTEXT[index]);
        }
    }

}
