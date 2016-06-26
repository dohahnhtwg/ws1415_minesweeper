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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import akka.actor.ActorRef;
import de.htwg.se.controller.messages.MainController.NewSizeRequest;

import static de.htwg.se.aview.gui.Constants.*;

public final class NewGameWindow extends JDialog {
    private static final long serialVersionUID = 1L;

    public NewGameWindow(final Frame owner, final ActorRef parentRef, final ActorRef controller) {
        super(owner, "New Game Mode", true);
        JPanel panel = new JPanel(new GridLayout(ONE, THREE));
        JButton small, medium, large;
        small = new JButton("Small");
        medium = new JButton("Medium");
        large = new JButton("Large");
        small.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        medium.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        large.setPreferredSize(new Dimension(DEF_BUT_SIZEX, DEF_BUT_SIZEY));
        small.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                PlayingFieldPanel.zeroMarked();
                controller.tell(new NewSizeRequest(SMALLSIZE, SMALLSIZE, SMALLMINES), parentRef);
                setVisible(false);
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                PlayingFieldPanel.zeroMarked();
                controller.tell(new NewSizeRequest(MEDIUMSIZE, MEDIUMSIZE, MEDIUMMINES), parentRef);
                setVisible(false);
            }
        });
        large.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                PlayingFieldPanel.zeroMarked();
                controller.tell(new NewSizeRequest(MEDIUMSIZE, LARGESIZE, LARGEMINES), parentRef);
                setVisible(false);
            }
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
