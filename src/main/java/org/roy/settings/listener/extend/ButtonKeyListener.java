package org.roy.settings.listener.extend;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class ButtonKeyListener implements KeyListener{

	private JButton button;

	public ButtonKeyListener(JButton button) {
		this.button = button;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
			button.doClick();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
