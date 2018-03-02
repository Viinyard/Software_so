package Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public abstract class Field extends JTextField implements KeyListener {
	
	private static final long serialVersionUID = -6359511272872247566L;
	
	public Field() {
		this.setColumns(5);
		this.addKeyListener(this);
	}
	
	public abstract void keyTyped(KeyEvent e);
	
	public void keyPressed(KeyEvent e) {};
	public void keyReleased(KeyEvent e) {};
}
