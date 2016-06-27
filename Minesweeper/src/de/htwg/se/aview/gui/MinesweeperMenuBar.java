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
import javax.swing.*;
import akka.actor.ActorRef;
import de.htwg.se.aview.messages.LoginResponse;
import de.htwg.se.aview.messages.NewAccountResponse;
import de.htwg.se.aview.messages.StatisticResponse;
import de.htwg.se.controller.messages.MainController.FinishGameMessage;
import de.htwg.se.controller.messages.MainController.LoginRequest;
import de.htwg.se.controller.messages.MainController.NewAccountRequest;
import de.htwg.se.controller.messages.MainController.StatisticRequest;
import de.htwg.se.controller.messages.RedoRequest;
import de.htwg.se.controller.messages.UndoRequest;
import de.htwg.se.minesweeper.messages.TerminateRequest;
import de.htwg.se.model.IStatistic;

class MinesweeperMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    private JLabel loginStatus;
    private ActorRef controller;
    private ActorRef parent;
    private JFrame loginFrame;
    private String loginName = "";
    private JButton login;

    MinesweeperMenuBar(final ActorRef controller, final ActorRef parent) {
        super();
        this.parent = parent;
        this.controller = controller;
        final JMenu fileMenu = new JMenu("Game");
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
        login = new JButton("Login");
        add(Box.createHorizontalGlue());
        add(this.loginStatus);
        add(login);
        createLoginFrame();
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText().equals("Logout")) {
                    controller.tell(new LoginRequest("Default", "Default"), parent);
                    loginName = "";
                } else {
                    loginFrame.setVisible(true);
                }
            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                controller.tell(new UndoRequest(), parent);
            }
        });

        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                controller.tell(new RedoRequest(), parent);
            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(fileMenu,
                        "Exit Minesweeper?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (n == JOptionPane.YES_OPTION) {
                    controller.tell(new FinishGameMessage(), parent);
                    parent.tell(new TerminateRequest(), parent);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                new NewGameWindow(null, parent, controller);
            }
        });

        statistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                controller.tell(new StatisticRequest(), parent);
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionListener) {
                JOptionPane.showMessageDialog(fileMenu,
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
                JOptionPane.showMessageDialog(fileMenu,
                        "<html>" + "<body>" + "<h1><font color='green'>Minesweeper</font></h1>"
                        + "Copyright (C) 2015 " +"<p>Dominik Hahn & Pavel Kravetskiy</p>"
                        + "<a href='http://www.gnu.org/licenses/gpl.txt'>Under GNU GPL v3 license</a>"
                        + "</body></html>", "License", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void showStatisticPopup(StatisticResponse statistic) {
        IStatistic stat = statistic.getStatistic();
        int games = stat.getGamesPlayed();
        int wins = stat.getGamesWon();
        String playedTime = stat.getTimeSpent() == 0 ? "0" :
                String.valueOf(stat.getTimeSpent() / 1000) + "s";
        String minTimePlayed = stat.getMinTime() == Long.MAX_VALUE ? "-" :
                String.valueOf(stat.getMinTime() / 1000) + "s";
        double percentage = games != 0 ? (wins * Constants.DEF_BUT_SIZEX / games) : 0;
        JOptionPane.showMessageDialog(null,
                "<html><body><table style='width:100%'>" +
                        "<tr><td>Played Time:</td><td>" + playedTime + "</td></tr>" +
                        "<tr><td>Fastest win:</td><td>" + minTimePlayed + "</td></tr>" +
                        "<tr><td>Played Games:</td><td>" + games + "</td></tr>" +
                        "<tr><td>Wins:</td><td>" + wins + "</td></tr>" +
                        "<tr><td>Win percentage:</td><td>" + percentage + "%</td></tr>" +
                        "</table></body></html>",
                "Statistic", JOptionPane.INFORMATION_MESSAGE);
    }

    private void placeComponents(final JPanel panel, final JFrame frame) {
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

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(180, 80, 80, 25);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginName = userText.getText();
                char[] password = passwordText.getPassword();
                passwordText.setText("");
                if (loginName.length() == 0 || password.length == 0) {
                    JOptionPane.showMessageDialog(frame, "Login and password can not be empty",
                            "Login", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.tell(new LoginRequest(loginName, String.valueOf(password)), parent);
                Arrays.fill(password, '0');
                loginFrame.setVisible(false);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginName = userText.getText();
                char[] password = passwordText.getPassword();
                if (loginName.length() == 0 || password.length == 0) {
                    JOptionPane.showMessageDialog(frame, "Login and password can not be empty",
                            "Registration", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.tell(new NewAccountRequest(loginName, String.valueOf(password)), parent);
                Arrays.fill(password, '0');
            }
        });
    }

    public void handleLoginResponse(LoginResponse loginResponse) {
        if (loginResponse.isSuccess()) {
            if (loginName.equals("")) {
                login.setText("Login");
                loginStatus.setText("");
            } else {
                login.setText("Logout");
                loginStatus.setText("<html><body>Logged in as <b style=\"color:red\">" + loginName + "</b></body></html>");
            }
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Wrong username or password",
                    "Login", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void handleNewAccountResponse(NewAccountResponse newAccountResponse) {
        if (newAccountResponse.getSuccess()) {
            JOptionPane.showMessageDialog(loginFrame, "Successfully registered as \"" + loginName + "\"",
                    "Registration", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Registration failed. User already exists or wrong input data",
                    "Registration", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void createLoginFrame() {
        loginFrame= new JFrame("Login/Register");
        loginFrame.setSize(300, 150);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel, loginFrame);
        loginFrame.setVisible(false);
    }
}
