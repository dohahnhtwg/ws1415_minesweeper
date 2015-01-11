package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;
import static de.htwg.se.aview.gui.Constances.*;

public final class NewGameWindow extends JDialog {

    private static final long serialVersionUID = 1L;

    public NewGameWindow(final MinesweeperMenuBar menu, final IController controller) {

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
            controller.create(SMALLSIZE, SMALLSIZE, SMALLMINES);
            setVisible(false);
        });
        medium.addActionListener(ActionListener -> {
            controller.create(MEDIUMSIZE, MEDIUMSIZE, MEDIUMMINES);
            setVisible(false);
        });
        large.addActionListener(ActionListener -> {
            controller.create(MEDIUMSIZE, LARGESIZE, LARGEMINES);
            setVisible(false);
        });

        panel.add(small);
        panel.add(medium);
        panel.add(large);

        setAlwaysOnTop(true);
        setLocationRelativeTo(menu);

        add(panel);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
