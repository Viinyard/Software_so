package Component.CustomTable;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public abstract class JCustomTableAbstractTableModel extends AbstractTableModel implements ColumnObjectChangeListener {

	private static final long serialVersionUID = 1L;

	protected final ColumnObject[] columns;
	protected ColumnObject[] columns_displayed;
	
	public JCustomTableAbstractTableModel(ColumnObject[] columns) {
		this.columns = columns;
		
		for(ColumnObject c : this.columns) {
			c.addColumnObjectChangeListener(this);
		}
		
		this.columns_displayed = this.getColumnsDisplayed();
	}
	
	protected final ColumnObject[] getColumnsDisplayed() {
		ArrayList<ColumnObject> alTemp = new ArrayList<ColumnObject>();
		for(ColumnObject c : this.columns) {
			if(c.isDisplayed()) alTemp.add(c);
		}
		
		return alTemp.toArray(new ColumnObject[alTemp.size()]);
	}
	
	@Override
	public final int getColumnCount() {
		return this.columns_displayed.length;
	}
	
	@Override
	public final String getColumnName(int column) {
		return this.columns_displayed[column].getColomn_name();
	}
	
	@Override
	public abstract int getRowCount();

	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);

	@Override
	public final void ColumnObjetChange(ColumnObjectChangeEvent e) {
		if(e.getNewState() != e.getOldState()) {
			this.columns_displayed = this.getColumnsDisplayed();
			this.fireTableStructureChanged();
		}
	}

}
