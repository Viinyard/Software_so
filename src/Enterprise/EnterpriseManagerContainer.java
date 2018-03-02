package Enterprise;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import Component.ActionBar.ActionBar;
import Component.CustomTable.JCustomTableControlPane;
import Google.Address.AddressManager.Component.StaticMap.ImgPanel;
import Google.Address.AddressManager.Component.StaticMap.StaticMap;

public class EnterpriseManagerContainer extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	
	private JTable enterpriseTable;
	private JCustomTableControlPane tableControlPane;
	private ImgPanel imgPanel;
	private StaticMap staticMap;
	private ActionBar actionBar;

	public EnterpriseManagerContainer() {
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		/*
		 * JTable
		 */
		this.enterpriseTable = new JTable();
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		this.enterpriseTable.setDefaultRenderer(Integer.class, cellRenderer);
		this.enterpriseTable.setDefaultRenderer(Double.class, cellRenderer);
		this.enterpriseTable.getSelectionModel().addListSelectionListener(this);
		
		JPanel jpTable = new JPanel(new BorderLayout());
		jpTable.add(this.enterpriseTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jspTable = new JScrollPane(this.enterpriseTable);
		this.add(jpTable, BorderLayout.CENTER);
		
		/*
		 * ControlTable
		 */
		this.tableControlPane = new JCustomTableControlPane();
		this.add(this.tableControlPane, BorderLayout.NORTH);
		
		/*
		 * ImagePanel
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
		 * ActionBar
		 */
		this.actionBar = new ActionBar(ActionBar.HORIZONTAL);
		this.actionBar.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(this.actionBar, BorderLayout.SOUTH);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
