package Component.CustomTable;

import java.util.EventObject;

public class ColumnObjectChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private ColumnObject columnObject;
	private boolean newState;
	private boolean oldState;

	public ColumnObjectChangeEvent(Object source, ColumnObject columnObject, boolean newState, boolean oldState) {
		super(source);
		this.columnObject = columnObject;
		this.newState = newState;
		this.oldState = oldState;
	}

	public ColumnObject getColumnObject() {
		return this.columnObject;
	}

	public boolean getNewState() {
		return this.newState;
	}

	public boolean getOldState() {
		return this.oldState;
	}

	@Override
	public String toString() {
		return "ColumnObjectChangeEvent [columnObject=" + columnObject + ", newState=" + newState + ", oldState="
				+ oldState + "]";
	}
}
