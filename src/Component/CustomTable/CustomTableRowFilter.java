package Component.CustomTable;

import javax.swing.RowFilter;

public class CustomTableRowFilter extends RowFilter<JCustomTableAbstractTableModel, Integer> {

	private String key = "";
	private String filter;
	
	@Override
	public boolean include(
			javax.swing.RowFilter.Entry<? extends JCustomTableAbstractTableModel, ? extends Integer> entry) {
		String rawString = "";
		int cptCol = 0;
			
		for(ColumnObject c : entry.getModel().columns_displayed) {
			if(this.filter == null || c.getColomn_name().equals(this.filter))
				rawString += c.isDisplayed() ? entry.getStringValue(cptCol) : "";
			cptCol++;
		}
		
		for(String k : this.key.toLowerCase().split(" ")) {
			if(!rawString.toLowerCase().contains(k)) return false;
		}
		
		return true;
	}

	protected void setKey(String key) {
		this.key = key;
	}
	
	protected void setFilter(String filter_name) {
		this.filter = filter_name;
	}
}
