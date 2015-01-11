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
        }
	}

}
