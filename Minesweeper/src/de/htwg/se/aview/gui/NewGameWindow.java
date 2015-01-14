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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;
import static de.htwg.se.aview.gui.Constants.*;

public final class NewGameWindow extends JDialog {

    private static final long serialVersionUID = 1L;

    public NewGameWindow(final IController controller) {

        super();
        setTitle("New Game Mode");
        setModal(true);
        JPanel panel = new JPanel(new GridLayout(ONE, THREE));
        JButton small, medium, large;
        small = new JButton("Small");
        medium = new JButton("Medium");
        large = new JButton("Large");
        small.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        medium.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        large.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        small.addActionListener(ActionListener -> {
            PlayingFieldPanel.zeroMarked();
            controller.create(SMALLSIZE, SMALLSIZE, SMALLMINES);
            setVisible(false);
        });
        medium.addActionListener(ActionListener -> {
            PlayingFieldPanel.zeroMarked();
            controller.create(MEDIUMSIZE, MEDIUMSIZE, MEDIUMMINES);
            setVisible(false);
        });
        large.addActionListener(ActionListener -> {
            PlayingFieldPanel.zeroMarked();
            controller.create(MEDIUMSIZE, LARGESIZE, LARGEMINES);
            setVisible(false);
        });

        panel.add(small);
        panel.add(medium);
        panel.add(large);

        setAlwaysOnTop(true);
        setBounds(BOUNDS, BOUNDS, BOUNDS, BOUNDS);
        add(panel);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
