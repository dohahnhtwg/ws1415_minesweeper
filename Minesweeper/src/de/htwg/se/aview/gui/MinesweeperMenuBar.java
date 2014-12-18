package de.htwg.se.aview.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MinesweeperMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private JMenu menu, menu2;
    private JMenuItem open, save, exit, newGame, statistik, info,  license;
    private JFileChooser fc;
    
//    private Business_Layer business_layer;

    public MinesweeperMenuBar() {
        menu = new JMenu("File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        newGame = new JMenuItem("New Game");
        statistik = new JMenuItem("Statistic");
        fc = new JFileChooser(".");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        menu.add(newGame);
        menu.addSeparator();
        menu.add(open);
        menu.add(save);
        menu.add(statistik);
        menu.addSeparator();
        menu.add(exit);
        add(menu);
        
        menu2 = new JMenu("?");
        info = new JMenuItem("Info");
        license = new JMenuItem("License");
        menu2.add(info);
        menu2.add(license);
        add(menu2);
        
        
        open.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
//                int returnVal = fc.showOpenDialog(this);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fc.getSelectedFile();
//                    business_layer.read(file);
//                }
                
            }
        });

        save.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
//                int returnVal = fc.showSaveDialog(this);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fc.getSelectedFile();
//                    business_layer.save(file);
//                }
                
            }
        });

        exit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
//                int n = JOptionPane.showConfirmDialog(this,
//                        "are u sure?",
//                        "Bestaetigung",
//                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//                if (n == JOptionPane.YES_OPTION)
                    System.exit(0);
                
            }
        });
    }

}
