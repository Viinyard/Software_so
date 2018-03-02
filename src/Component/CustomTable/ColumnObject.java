package Component.CustomTable;

import javax.swing.event.EventListenerList;

public class ColumnObject {
	
	private String colomn_name;
	private Class<?> column_class;
	private boolean displayed;
	
	private EventListenerList listeners = new EventListenerList();
	
	public ColumnObject(String column_name, Class<?> column_class, boolean displayed) {
		this.colomn_name = column_name;
		this.column_class = column_class;
		this.displayed = displayed;
	}
	
	public void addColumnObjectChangeListener(ColumnObjectChangeListener listener) {
		this.listeners.add(ColumnObjectChangeListener.class, listener);
	}
	
	public void removeColumnObjectChangeListener(ColumnObjectChangeListener listener) {
		this.listeners.remove(ColumnObjectChangeListener.class, listener);
	}
	
	public ColumnObjectChangeListener[] getColumnObjectListener() {
		return this.listeners.getListeners(ColumnObjectChangeListener.class);
	}

	public String getColomn_name() {
		return this.colomn_name;
	}

	public Class<?> getColumn_class() {
		return this.column_class;
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public void setDisplayed(boolean displayed) {
		boolean old = this.displayed;
		this.displayed = displayed;
		
		for(ColumnObjectChangeListener listener : this.getColumnObjectListener()) {
			listener.ColumnObjetChange(new ColumnObjectChangeEvent(this, this, this.displayed, old));
		}
	}
}
