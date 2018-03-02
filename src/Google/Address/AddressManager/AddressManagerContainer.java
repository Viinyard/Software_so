package Google.Address.AddressManager;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import Component.ActionBar.ActionBar;
import Component.CustomTable.JCustomTableControlPane;
import Google.Address.AddressManager.Component.AddressForm;
import Google.Address.AddressManager.Component.StaticMap.ImgPanel;
import Google.Address.AddressManager.Component.StaticMap.StaticMap;

public class AddressManagerContainer extends JPanel implements Observer, ListSelectionListener  {

	private static final long serialVersionUID = 1L;
	
	protected JTable addressTable;
	protected ActionBar actionBar;
	protected StaticMap staticMap;
	protected AddressForm addressForm;
	
	protected JCustomTableControlPane tableControlPane;
	
	private ImgPanel imgPanel;
	
	private EventListenerList listeners = new EventListenerList();
	
	public AddressManagerContainer() {
		this.initGUI();
	}
	
	public void addAddressSelectionListener(AddressSelectionListener listener) {
		this.listeners.add(AddressSelectionListener.class, listener);
	}
	
	public void removeAddressSelectionListener(AddressSelectionListener listener) {
		this.listeners.remove(AddressSelectionListener.class, listener);
	}
	
	public AddressSelectionListener[] getAddressSelectionListener() {
		return this.listeners.getListeners(AddressSelectionListener.class);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		/*
		 * JTable
		 */
		this.addressTable = new JTable();
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		this.addressTable.setDefaultRenderer(Integer.class, cellRenderer);
		this.addressTable.setDefaultRenderer(Double.class, cellRenderer);
		this.addressTable.getSelectionModel().addListSelectionListener(this);
		
		JPanel jpTable = new JPanel(new BorderLayout());
		jpTable.add(this.addressTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jspTable = new JScrollPane(this.addressTable);
		jpTable.add(jspTable, BorderLayout.CENTER);
		
		/*
		 * Control Table
		 */
		this.tableControlPane = new JCustomTableControlPane();
		this.add(this.tableControlPane, BorderLayout.NORTH);
		
		/*
		 * Image Panel
		 */
		this.imgPanel = new ImgPanel();
		this.imgPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.staticMap = new StaticMap();
		this.staticMap.addImageDownloadListener(this.imgPanel);
		
		/*
		 * JTable & Image Panel
		 * JSplitPane
		 */
		JSplitPane jspTableMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jpTable, this.imgPanel);
		
		jspTableMap.setOneTouchExpandable(true);
		
		this.add(jspTableMap, BorderLayout.CENTER);
		
		/*
		 * AddressForm
		 */
		this.addressForm = new AddressForm(false);
		this.addressForm.setBorder(BorderFactory.createLoweredBevelBorder());

		/*
		 * Action Bar
		 */
		this.actionBar = new ActionBar(ActionBar.CENTER);
		this.actionBar.setBorder(BorderFactory.createLoweredBevelBorder());
		
		/*
		 * AddressForm & Button Panel
		 * JPanel
		 */
		JPanel jpSouth = new JPanel(new BorderLayout());
		jpSouth.add(this.addressForm, BorderLayout.CENTER);
		jpSouth.add(this.actionBar, BorderLayout.EAST);
		
		this.add(jpSouth, BorderLayout.SOUTH);
		
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(this.addressTable.getSelectionModel()) && !e.getValueIsAdjusting()) {
			int[] sortedRow = this.addressTable.getSelectedRows();
			int sortedIndex = this.addressTable.getSelectedRow() != -1 ?  this.addressTable.convertRowIndexToModel(this.addressTable.getSelectedRow()) : -1;
			int cptInd = 0;
			for(int ind : this.addressTable.getSelectedRows()) {
				sortedRow[cptInd] = this.addressTable.convertRowIndexToModel(ind);
				cptInd++;
			}
			
			for(AddressSelectionListener listener : this.getAddressSelectionListener()) {
				listener.AddressSelectionChange(new AddressSelectionEvent(this, sortedIndex, sortedRow));
			}
		}
	}
}
