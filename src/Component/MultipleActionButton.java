package Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.event.EventListenerList;

public class MultipleActionButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, EventListenerList> actionListeners;
	
	private int action;
	public static final int defaultAction = -1;
	
	public MultipleActionButton() {
		super();
		
		this.action = MultipleActionButton.defaultAction;
		this.actionListeners = new HashMap<Integer, EventListenerList>();
	}

	public void setAction(int action) {
		this.action = action;
	}
	
	@Override
	public void addActionListener(ActionListener listener) {
		if(!this.actionListeners.containsKey(MultipleActionButton.defaultAction)) {
			this.actionListeners.put(MultipleActionButton.defaultAction, new EventListenerList());
		}
		
		this.actionListeners.get(MultipleActionButton.defaultAction).add(ActionListener.class, listener);
 	}
	
	public void addActionListener(int action, ActionListener listener) {
		if(!this.actionListeners.containsKey(action)) {
			this.actionListeners.put(action, new EventListenerList());
		}
		
		this.actionListeners.get(action).add(ActionListener.class, listener);
 	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(ActionListener listener : this.actionListeners.get(this.action).getListeners(ActionListener.class)) {
			listener.actionPerformed(e);
		}
	}
}
