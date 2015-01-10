package de.htwg.se.aview.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import de.htwg.se.controller.IController;

public class MinesweeperMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private JMenu menu, menu2;
    private JMenuItem undo, redo, exit, newGame, statistik, info,  license;
    private JFileChooser fc;
    
    private final IController controller;

    public MinesweeperMenuBar(final IController controller) {
        this.controller = controller;
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK));
        redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK));
        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));
        statistik = new JMenuItem("Statistic");
        statistik.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK));
        fc = new JFileChooser(".");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        menu.add(newGame);
        menu.addSeparator();
        menu.add(undo);
        menu.add(redo);
        menu.add(statistik);
        menu.addSeparator();
        menu.add(exit);
        add(menu);
        
        menu2 = new JMenu("?");
        menu2.setMnemonic(KeyEvent.VK_I);
        info = new JMenuItem("Info");
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                InputEvent.CTRL_DOWN_MASK));
        license = new JMenuItem("License");
        license.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                InputEvent.CTRL_DOWN_MASK));
        menu2.add(info);
        menu2.add(license);
        add(menu2);
        
        
        undo.addActionListener(ActionListener -> controller.undo());

        redo.addActionListener(ActionListener -> controller.redo());

        exit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(MinesweeperMenuBar.this,
                        "are u sure?",
                        "Bestaetigung",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (n == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });

        newGame.addActionListener(ActionListener -> controller.create());


        statistik.addActionListener(ActionListener -> JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                "Time spend: " + BottomInfoPanel.getTime(),
			    "Statistic", JOptionPane.INFORMATION_MESSAGE));


        info.addActionListener(ActionListener -> JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
				        "This program was created by\nDominik Hahn & Pavel Kravetskiy", "Information",
				        JOptionPane.INFORMATION_MESSAGE));

        license.addActionListener(ActionListener -> JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                "under construction", "License", JOptionPane.PLAIN_MESSAGE));
    }

}
