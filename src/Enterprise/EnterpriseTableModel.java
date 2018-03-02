package Enterprise;

import Component.CustomTable.ColumnObject;
import Component.CustomTable.JCustomTableAbstractTableModel;

public class EnterpriseTableModel extends JCustomTableAbstractTableModel {

	private static final long serialVersionUID = 1L;

	public EnterpriseTableModel() {
		super(new ColumnObject[] {
				new ColumnObject("id", Long.class, false),
				new ColumnObject("label", String.class, true),
				new ColumnObject("creationDate", Long.class, false),
				new ColumnObject("taxYear", Long.class, false),
				new ColumnObject("siretNumber", String.class, false),
		});
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
