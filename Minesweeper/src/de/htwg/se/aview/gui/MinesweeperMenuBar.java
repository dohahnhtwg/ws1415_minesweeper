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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

import de.htwg.se.controller.IController;
import de.htwg.se.model.IStatistic;

class MinesweeperMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private JLabel loginStatus;
    private final IController controller;

    MinesweeperMenuBar(final IController controller) {
        this.controller = controller;
        JMenu fileMenu = new JMenu("Game");
        fileMenu.setMnemonic(KeyEvent.VK_G);
        JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK));
        JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK));
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));
        JMenuItem statistic = new JMenuItem("Statistic");
        statistic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(newGame);
        fileMenu.addSeparator();
        fileMenu.add(undo);
        fileMenu.add(redo);
        fileMenu.add(statistic);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_I);
        JMenuItem info = new JMenuItem("About...");
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                InputEvent.CTRL_DOWN_MASK));
        JMenuItem license = new JMenuItem("License");
        license.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                InputEvent.CTRL_DOWN_MASK));
        helpMenu.add(info);
        helpMenu.add(license);
        add(fileMenu);
        add(helpMenu);
        this.loginStatus = new JLabel("");
        JButton login = new JButton("Login");
        add(Box.createHorizontalGlue());
        add(this.loginStatus);
        add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Login/Register");
                frame.setSize(300, 150);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                JPanel panel = new JPanel();
                frame.add(panel);
                placeComponents(panel, frame);

                frame.setVisible(true);
            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                controller.undo();
            }
        });

        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                controller.redo();
            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(MinesweeperMenuBar.this,
                        "Exit Minesweeper?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (n == JOptionPane.YES_OPTION) {
                    controller.finishGame();
                    System.exit(0);
                }
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                new NewGameWindow(controller);
            }
        });

        statistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                final IStatistic stat = controller.getUserStatistic();
                final int games = stat.getPlayedGames();
                final int wins = stat.getWonGames();
                final String playedTime = stat.getPlayedTime() == 0 ? "-" : String.valueOf(stat.getPlayedTime() / 1000);
                final String minTimePlayed = stat.getMinTimePlayed() == Long.MAX_VALUE ? "-" :
                        String.valueOf(stat.getMinTimePlayed() / 1000);
                double percentage = 0;
                if ((games) != 0) {
                    percentage = wins * Constants.DEF_BUT_SIZEX/(games);
                }
                JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                        "<html><body><table style='width:100%'>" +
                                "<tr><td>Played Time:</td><td>" + playedTime + "s</td></tr>" +
                                "<tr><td>Fastest win:</td><td>" + minTimePlayed + "s</td></tr>" +
                                "<tr><td>Played Games:</td><td>" + games + "</td></tr>" +
                                "<tr><td>Wins:</td><td>" + wins + "</td></tr>" +
                                "<tr><td>Percentage:</td><td>" + percentage + "%</td></tr>" +
                                "</table></body></html>",
                        "Statistic", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                        "<html><body>" + "<h3>Minesweeper</h3>"
                        + "<p>This game was created to improve</p>"+"<p>understanding of the software engineering basics</p>"
                        + "<p>like <i>life cycle of a software project</i>, <i>version control project</i>,</p>"
                        + "<p><i>task management<i> etc.</p>"
                        + "<p><b>by Dominik Hahn & Pavel Kravetskiy</b></p>" + "<hr />Software Engineering WS2014/2015"
                        + "</body></html>", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        license.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                        "<html>" + "<body>" + "<h1><font color='green'>Minesweeper</font></h1>"
                        + "Copyright (C) 2015 " +"<p>Dominik Hahn & Pavel Kravetskiy</p>"
                        + "<a href='http://www.gnu.org/licenses/gpl.txt'>Under GNU GPL v3 license</a>"
                        + "</body></html>", "License", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void placeComponents(JPanel panel, final JFrame frame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        final JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        final JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        final JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 80, 80, 25);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText();
                char[] password = passwordText.getPassword();
                boolean ok = controller.logIn(name, Arrays.toString(password));
                Arrays.fill(password, '0');
                if (ok) {
                    loginStatus.setText("<html><body>Logged in as <b style=\"color:red\">" + name + "</b></body></html>");
                    loginButton.setText("Logout");
                } else {
                    JOptionPane.showMessageDialog(frame, "Wrong username or password",
                            "Login", JOptionPane.WARNING_MESSAGE);
                }
                frame.setVisible(false);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText();
                char[] password = passwordText.getPassword();
                boolean ok = controller.addNewAccount(name, Arrays.toString(password));
                Arrays.fill(password, '0');
                if (ok) {
                    JOptionPane.showMessageDialog(frame, "Successfully registered as \"" + name + "\"",
                            "Registration", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed. User already exists or wrong input data",
                            "Registration", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
