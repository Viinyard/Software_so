package Component.CustomTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableRowSorter;

import Google.Address.AddressManager.Component.AddressFrame;

public class JCustomTableControler implements ActionListener, ColumnObjectChangeListener {
	
	private DefaultComboBoxModel<String> jcbModel;
	private JCustomTableControlPane controlTablePane;
	
	private JCustomTableAbstractTableModel tableModel;
	
	private CustomTableRowFilter rowFilter;
	private TableRowSorter<JCustomTableAbstractTableModel> sorter;
	
	/**
	 * 1. Add a JCustomTableControlPane where you want to your container
	 * Create a JCustomTableControler with your own JTableModel that extends a JCustomTableAbstractTableModel
	 * And the JCustomTableControlPane.
	 * 
	 * 2. Add the The TableRowSorter to you JTable with the method getTableSorter
	 * 
	 * 3. That's work
	 */
	
	public JCustomTableControler(JCustomTableAbstractTableModel tableModel, JCustomTableControlPane controlTablePane) {
		this.tableModel = tableModel;
		this.controlTablePane = controlTablePane;
		
		/*
		 * Sorter / Filter
		 */
		this.sorter = new TableRowSorter<JCustomTableAbstractTableModel>(this.tableModel);
		this.rowFilter = new CustomTableRowFilter();
		this.sorter.setRowFilter(this.rowFilter);
		
		this.jcbModel = new DefaultComboBoxModel<String>();
		this.controlTablePane.jcbColumn.setModel(this.jcbModel);
		
		this.jcbModel.addElement(null);
		for(ColumnObject c : this.tableModel.columns_displayed) {
			this.jcbModel.addElement(c.getColomn_name());
		}
		/*
		 * Listeners
		 */
		this.controlTablePane.jcbColumn.addActionListener(this);
		this.controlTablePane.jtfSearchBar.addActionListener(this);
		this.controlTablePane.jbSearch.addActionListener(this);
		this.controlTablePane.jbSettings.addActionListener(this);
		
		for(ColumnObject c : this.tableModel.columns) {
			c.addColumnObjectChangeListener(this);
		}
	}
	
	public TableRowSorter<JCustomTableAbstractTableModel> getTableSorter() {
		return this.sorter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.controlTablePane.jcbColumn)) {
			this.rowFilter.setFilter((String) this.jcbModel.getSelectedItem());
			this.controlTablePane.jcbColumn.getSelectedItem();
		} else if(e.getSource().equals(this.controlTablePane.jtfSearchBar) || e.getSource().equals(this.controlTablePane.jbSearch)) {
			this.rowFilter.setKey(this.controlTablePane.jtfSearchBar.getText());
		} else if(e.getSource().equals(this.controlTablePane.jbSettings)) {
			if(this.tableModel.columns.length > 0 && !AddressFrame.isAlreadyUsed()) {
				new JCustomTableConfigDisplayedColumnContainer(this.tableModel.columns.clone());
			}
		}
		this.tableModel.fireTableStructureChanged();
	}

	@Override
	public void ColumnObjetChange(ColumnObjectChangeEvent e) {
		if(e.getOldState() && !e.getNewState()) {
			this.jcbModel.removeElement(e.getColumnObject().getColomn_name());
		}
		
		if(!e.getOldState() && e.getNewState()) {
			this.jcbModel.addElement(e.getColumnObject().getColomn_name());
		}
	}
}
