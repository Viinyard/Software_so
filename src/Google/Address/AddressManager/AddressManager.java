package Google.Address.AddressManager;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Component.ActionBar.ActionBarEvent;
import Component.ActionBar.ActionBarListener;
import Component.CustomTable.JCustomTableControler;
import Data.AbstractDAOFactory;
import Entity.Address;
import Entity.Attachment;
import Google.Address.AddressManager.Component.AddressFrame;
import Google.Address.AddressManager.Edit.AddressEditor;
import Google.Address.AddressManager.Edit.EditedAddressEvent;
import Google.Address.AddressManager.Edit.EditedAddressListener;
import Google.Address.AddressManager.New.AddressMaker;
import Google.Address.AddressManager.New.PlaceResearch.SearchAddressResponseEvent;
import Google.Address.AddressManager.New.PlaceResearch.SearchAddressResponseListener;
import Google.Address.AddressManager.Trash.AddressTrasher;
import Google.Address.AddressManager.Trash.TrashedAddressEvent;
import Google.Address.AddressManager.Trash.TrashedAddressListener;

public class AddressManager implements ActionBarListener, SearchAddressResponseListener, AddressSelectionListener, TrashedAddressListener, EditedAddressListener {

	private AddressManagerModel addressManagerModel;
	private AddressManagerContainer addressManagerContainer;
	private AddressTableModel addressTableModel;
	
	public AddressManager(AddressManagerContainer addressManagerContainer) {
		
		/*
		 * Général Model / Vue
		 */
		this.addressManagerModel = new AddressManagerModel();
		this.addressManagerContainer = addressManagerContainer;
		
		/*
		 * Model Table Data : Address
		 */
		this.addressTableModel = new AddressTableModel();
		
		this.addressManagerContainer.addressTable.setModel(this.addressTableModel);
		
		//TableRowSorter<AddressTableModel> sorter = new TableRowSorter<AddressTableModel>(this.addressTableModel);
		
		JCustomTableControler tablectrl = new JCustomTableControler(this.addressTableModel, this.addressManagerContainer.tableControlPane);
		this.addressManagerContainer.addressTable.setRowSorter(tablectrl.getTableSorter());
		
		//this.addressManagerContainer.addressTable.setRowSorter(sorter);
		
		
		    
		this.addressTableModel.setAddresses(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().findAll());
		
		this.addSelectionListeners();
		this.addSelectionObservers();
	}
	
	private void addSelectionListeners() {
		this.addressManagerContainer.actionBar.addActionBarListener(this);
		this.addressManagerContainer.addAddressSelectionListener(this);
	}
	
	@Override
	public void actionBarPerformed(ActionBarEvent e) {
		if(e.getCode() == ActionBarEvent.ADD) {
			if(!AddressFrame.isAlreadyUsed()) {
				new AddressMaker().addSearchAddressResponseListener(this);
			}
		}
		
		if(e.getCode() == ActionBarEvent.INFO) {
			try {
				URI uri = URI.create(this.addressManagerModel.getAddress().getUrl());
				Desktop.getDesktop().browse(uri);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getCode() == ActionBarEvent.DELETE) {
			if(!AddressFrame.isAlreadyUsed()) {
				if(this.addressManagerModel.getAddressesSelected().size() > 0) {
					new AddressTrasher(this.addressManagerModel.getAddressesSelected()).addTrashedAddressListener(this);;
				}
			}
		}
		
		if(e.getCode() == ActionBarEvent.EDIT) {
			if(!AddressFrame.isAlreadyUsed()) {
				if(this.addressManagerModel.getAddressesSelected().size() == 1) {
					new AddressEditor(this.addressManagerModel.getAddress()).addEditedAddressListener(this);;
				}
			}
		}
	}
	
	private void addSelectionObservers() {
		this.addressManagerModel.addObserver(this.addressManagerContainer.addressForm);
		this.addressManagerModel.addObserver(this.addressManagerContainer.staticMap);
		this.addressManagerModel.addObserver(this.addressManagerContainer.actionBar);
		this.addressManagerModel.clearAddressesSelected();
	}

	@Override
	public void findAddress(SearchAddressResponseEvent e) {
		Attachment attachment = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write( e.getImage(), "png", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			attachment = new Attachment("Photo", imageInByte);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
			

		Address address = e.getAddress();
		address.setId(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().create(e.getAddress()));
		
		if(address.getId() > 0) {
			if(attachment != null) {
				e.getAddress().addAttachment(attachment);
			}
			this.addressTableModel.addAddress(address);
		}
	}
	
	@Override
	public void AddressSelectionChange(AddressSelectionEvent e) {
		ArrayList<Address> alTemp = new ArrayList<Address>();
		for(int index : e.getAddressesSelected()) {
			alTemp.add(this.addressTableModel.getAddressAt(index));
		}
		this.addressManagerModel.setAddressesSelected(alTemp);
		
		this.addressManagerModel.setAddress(this.addressTableModel.getAddressAt(e.getAddressSelected()));
	}
	
	@Override
	public void TrashedAddress(TrashedAddressEvent e) {

		for(Address address : e.getTrashedAddresses()) {
			address.removeAllAttachment();
			AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().delete(address);
		}
		
		this.addressTableModel.removeAddresses(e.getTrashedAddresses());
		this.addressManagerModel.clearAddressesSelected();
	}

	@Override
	public void addressEdited(EditedAddressEvent e) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().update(e.getAddress());
	}
}
