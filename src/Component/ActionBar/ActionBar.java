package Component.ActionBar;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.EventListenerList;

public class ActionBar extends JPanel implements ActionListener, Observer {
	
	private static final long serialVersionUID = 1L;

	/*
	 * Layout Constant
	 */
	public static final int CENTER = 0;
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	private final String[] jbNames = new String[] {
			"SUPPRIMER",
			"AJOUTER",
			"EDITER",
			"INFORMATION",
			"CHERCHER"
	};
	
	private EventListenerList listeners = new EventListenerList();
	
	private ImageIcon[] jbIcons;
	
	private JButton[] jbuttons;
	
	public ActionBar(int layout) {
		switch(layout) {
		case ActionBar.CENTER :
			this.setLayout(new GridLayout(2, 2));
			break;
		case ActionBar.HORIZONTAL :
			this.setLayout(new GridLayout(1, 4));
			break;
		case ActionBar.VERTICAL :
			this.setLayout(new GridLayout(4, 1));
			break;
		}
		this.initGUI();
	}
	
	
	
	private void initGUI() {
		this.jbuttons = new JButton[4];
		
		this.jbIcons = new ImageIcon[4];
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"tag_trash"+ext);
			this.jbIcons[ActionBarEvent.DELETE] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_add"+ext);
			this.jbIcons[ActionBarEvent.ADD] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));			
			
			url = this.getClass().getClassLoader().getResource(path+"tag_screen"+ext);
			this.jbIcons[ActionBarEvent.EDIT] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));			
			
			url = this.getClass().getClassLoader().getResource(path+"tag_info"+ext);
			this.jbIcons[ActionBarEvent.INFO] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Font font = new Font("Courrier", Font.PLAIN, 18);
		
		for(int i = 0; i < this.jbuttons.length; i++) {
			this.jbuttons[i] = new JButton(this.jbNames[i]);
			this.jbuttons[i].setToolTipText(this.jbNames[i]);
			this.jbuttons[i].setFont(font);
			this.jbuttons[i].setIcon(this.jbIcons[i]);
			this.jbuttons[i].setHorizontalAlignment(SwingConstants.LEFT);
			this.jbuttons[i].addActionListener(this);
			this.add(this.jbuttons[i]);
		}
	}
	
	public void addActionBarListener(ActionBarListener listener) {
		this.listeners.add(ActionBarListener.class, listener);
	}
	
	public void removeActionBarListener(ActionBarListener listener) {
		this.listeners.remove(ActionBarListener.class, listener);
	}
	
	public ActionBarListener[] getActionBarListener() {
		return this.listeners.getListeners(ActionBarListener.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.jbuttons[ActionBarEvent.ADD])) {
			for(ActionBarListener listener : this.getActionBarListener()) {
				listener.actionBarPerformed(new ActionBarEvent(this, ActionBarEvent.ADD));
			}
		} else if(e.getSource().equals(this.jbuttons[ActionBarEvent.DELETE])) {
			for(ActionBarListener listener : this.getActionBarListener()) {
				listener.actionBarPerformed(new ActionBarEvent(this, ActionBarEvent.DELETE));
			}
		} else if(e.getSource().equals(this.jbuttons[ActionBarEvent.EDIT])) {
			for(ActionBarListener listener : this.getActionBarListener()) {
				listener.actionBarPerformed(new ActionBarEvent(this, ActionBarEvent.EDIT));
			}
		} else if(e.getSource().equals(this.jbuttons[ActionBarEvent.INFO])) {
			for(ActionBarListener listener : this.getActionBarListener()) {
				listener.actionBarPerformed(new ActionBarEvent(this, ActionBarEvent.INFO));
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ArrayList<?>) {
			this.jbuttons[0].setEnabled(((ArrayList<?>) arg).size() > 0);
			this.jbuttons[2].setEnabled(((ArrayList<?>) arg).size() == 1);
			this.jbuttons[3].setEnabled(((ArrayList<?>) arg).size() > 0);
		}
	}	
}
