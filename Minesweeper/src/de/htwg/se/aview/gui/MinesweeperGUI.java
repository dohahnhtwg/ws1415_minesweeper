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

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.inject.Inject;

import de.htwg.se.aview.messages.*;
import de.htwg.se.controller.messages.MainController.FieldResponse;
import de.htwg.se.controller.messages.MainController.FinishGameMessage;
import de.htwg.se.controller.messages.MainController.RegisterRequest;
import de.htwg.se.controller.messages.RevealCellResponse;
import de.htwg.se.model.IField;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public final class MinesweeperGUI extends UntypedActor {
    private static final long serialVersionUID = 1L;
    private ActorRef controller;
    private JPanel mainPanel;
    private MinesweeperMenuBar menu;
    private BottomInfoPanel bip;
    private PlayingFieldPanel field;
    private Thread timer;
    private JFrame window;

    @Inject
    public MinesweeperGUI(final ActorRef controller) {
        this.controller = controller;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, e);
        }
        window = new JFrame("Minesweeper");
        window.setLocationByPlatform(true);
        window.setResizable(false);
        mainPanel = new JPanel(new BorderLayout());
        window.setContentPane(mainPanel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        menu = new MinesweeperMenuBar(controller, self());
        mainPanel.add(menu, BorderLayout.NORTH);
        JPanel sidePanel1 = new JPanel();
        mainPanel.add(sidePanel1, BorderLayout.EAST);
        JPanel sidePanel2 = new JPanel();
        mainPanel.add(sidePanel2, BorderLayout.WEST);
        bip = new BottomInfoPanel();
        mainPanel.add(bip, BorderLayout.SOUTH);

        if (timer != null) {
            timer.interrupt();
        }
        timer = new Thread(new UpdaterThread(controller, self()));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.tell(new FinishGameMessage(), self());
            }
        });
        controller.tell(new RegisterRequest(), self());
    }

    private void constructMinesweeperGUI(final ActorRef controller, IField playingField) {

        if (field != null) {
            mainPanel.remove(field);
        }
        field = new PlayingFieldPanel(playingField, controller, self());
        mainPanel.add(field, BorderLayout.CENTER);

        window.setVisible(true);
        window.pack();
        window.repaint();
    }

    private void update(UpdateMessage msg) {
        constructMinesweeperGUI(controller, msg.getField());
        if(msg.getField().isVictory())  {
            action("Congratulation, You won the game!", msg.getCurrentTime());
            PlayingFieldPanel.zeroMarked();
            timer.interrupt();
            field.revealField();
        }
        if(msg.getField().isGameOver())   {
            action("GAME OVER!!!", msg.getCurrentTime());
            PlayingFieldPanel.zeroMarked();
            timer.interrupt();
            field.revealField();
        }
    }

    private void action(final String text, Long time) {
        JOptionPane.showMessageDialog(window, text + "\nTime: " + time + " Seconds",
                "Game ended", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FieldResponse) {
            constructMinesweeperGUI(controller, ((FieldResponse) message).getField());
        }
        if (message instanceof UpdateMessage) {
            update((UpdateMessage) message);
            bip.setCounterText(String.valueOf(((UpdateMessage) message).getField().getnMines()));
            bip.setTimer(((UpdateMessage) message).getCurrentTime());
            if (!timer.isAlive()) {
                timer.start();
            }
        }
        if (message instanceof RevealCellResponse) {
            field.updateField(((RevealCellResponse) message).getField());
        }
        if (message instanceof TimeResponse) {
            bip.setTimer(((TimeResponse) message).getCurrentTime());
        }
        if (message instanceof StatisticResponse) {
            menu.showStatisticPopup((StatisticResponse) message);
        }
        if (message instanceof LoginResponse) {
            menu.handleLoginResponse((LoginResponse) message);
        }
        if (message instanceof NewAccountResponse) {
            menu.handleNewAccountResponse((NewAccountResponse) message);
        }
        unhandled(message);
    }
}
