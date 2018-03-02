package Component.ActionBar;

import java.util.EventObject;

public class ActionBarEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	private int code;
	
	/*
	 * Identifier Constant
	 */
	public static final int DELETE = 0;
	public static final int ADD = 1;
	public static final int EDIT = 2;
	public static final int INFO = 3;

	public ActionBarEvent(Object source, int code) {
		super(source);
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}

}
