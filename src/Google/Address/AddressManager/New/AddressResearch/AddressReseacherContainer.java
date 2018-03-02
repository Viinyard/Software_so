package Google.Address.AddressManager.New.AddressResearch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import Google.Address.AddressManager.Component.AddressFrame;
import Google.Address.AddressManager.Data.Result;

public class AddressReseacherContainer extends AddressFrame implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField jtfSearchBar;
	private JButton jbSearch;
	private JList<Result> listResult;
	
	public AddressReseacherContainer() {
		super("LMG 3 - Address Finder", AddressFrame.SEARCH);
		this.initGUI();
		this.pack();
		this.setMinimumSize(this.getSize());
	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.jtfSearchBar = new JTextField(38);
		
		this.jbSearch = new JButton();
		
		JPanel jpSearch = new JPanel(new BorderLayout());
		jpSearch.add(this.jtfSearchBar, BorderLayout.CENTER);
		jpSearch.add(this.jbSearch, BorderLayout.EAST);
		
		this.listResult = new JList<Result>();
		this.listResult.setPreferredSize(new Dimension(0, 100));
		this.listResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listResult.setCellRenderer(new ResultListCellRenderer());
		this.listResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.add(jpSearch, BorderLayout.NORTH);
		this.add(this.listResult, BorderLayout.CENTER);
	}
	
	protected void addDocumentListener(DocumentListener listener) {
		this.jtfSearchBar.getDocument().addDocumentListener(listener);
	}
	
	protected void addListSelectionListener(ListSelectionListener listener) {
		this.listResult.addListSelectionListener(listener);
	}
	
	protected void addActionListener(ActionListener listener) {
		this.jtfSearchBar.addActionListener(listener);
		this.jbSearch.addActionListener(listener);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.setVisible(true);
		if(o instanceof AddressReseacherModel) {
			this.jbSearch.setIcon(((AddressReseacherModel) o).getIcon());
			this.listResult.setListData(((AddressReseacherModel) o).getResults());
		}
	}
}