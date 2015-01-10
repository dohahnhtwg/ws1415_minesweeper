package de.htwg.se.aview.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import de.htwg.se.controller.IController;

public final class ButtonHandler implements ActionListener {

	private int x, y;
	private IController controller;
	private PlayingFieldPanel field;
	private int retVal;
	private Object[] options = {"Exit", "New Game", "Cancel"};
	
	public ButtonHandler(final int x, final int y, PlayingFieldPanel p,  IController controller) {
		this.x = x;
		this.y = y;
		field = p;
		this.controller = controller;
		retVal = - Constances.ONE;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == field.getButtons()[x][y]) {
            controller.revealField(x + Constances.ONE, y + Constances.ONE);
            if (controller.isVictory())
            	action("Congratulation! You win the game!");
            if (controller.isGameOver())
            	action("You lose");
		}
	}

	
	private void action(final String text) {
		BottomInfoPanel.stopTimer();
        retVal = JOptionPane.showOptionDialog(null, text, "Spiel beendet",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[Constances.TWO]);
        if (retVal == Constances.ZERO)
        	System.exit(Constances.ZERO);
        else if (retVal == Constances.ONE) {
        	controller.create();
        }
	}
}
