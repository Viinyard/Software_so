package Component;

import java.awt.event.KeyEvent;

public class JIntegerField extends Field {
	
	private static final long serialVersionUID = 4732557830282525522L;
	
	public void keyTyped(KeyEvent e) {
		if(!(Character.isDigit(e.getKeyChar()) 
				|| e.getKeyChar() == KeyEvent.VK_BACK_SPACE 
				|| e.getKeyChar() == KeyEvent.VK_DELETE)) {
			e.consume();
		}
	}
	
	public int getIntegerValue() {
		if(this.getText().length() > 0) {
			return Integer.parseInt(this.getText());
		}
		return 0;
	}
}
