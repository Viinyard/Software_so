package Component.CustomTable;

import java.awt.GridLayout;

import Google.Address.AddressManager.Component.AddressFrame;

public class JCustomTableConfigDisplayedColumnContainer extends AddressFrame {
	
	private static final long serialVersionUID = 1L;

	private JCheckboxLabel[] checkboxColumn;
	
	public JCustomTableConfigDisplayedColumnContainer(ColumnObject[] columnObjectArray) {
		super("LMG 3 - Table Config", AddressFrame.CONFIG);
		
		this.checkboxColumn = new JCheckboxLabel[columnObjectArray.length];
		int index = 0;
		for(ColumnObject c : columnObjectArray) {
			this.checkboxColumn[index] = new JCheckboxLabel(c);
			index++;
		}
		
		this.initGUI();
		
		this.pack();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setLayout(new GridLayout(this.checkboxColumn.length, 1));
		
		for(JCheckboxLabel jcb : this.checkboxColumn) {
			this.add(jcb);
		}
	}
}
