package Google.Address.AddressManager.Trash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Entity.Address;

public class AddressTrasher implements ListSelectionListener, ActionListener {
	
	private AddressTrasherContainer addressTrasherContainer;
	private AddressTrasherModel addressTrasherModel;
	
	private EventListenerList listeners = new EventListenerList();
	
	public AddressTrasher(ArrayList<Address> addresses) {
		this.addressTrasherContainer = new AddressTrasherContainer();
		this.addressTrasherContainer.listAddress.setModel(new ListAddressModel(addresses));
		
		this.addressTrasherModel = new AddressTrasherModel();
		
		this.addressTrasherContainer.pack();
		this.addressTrasherContainer.setVisible(true);
		this.addressTrasherContainer.listAddress.addListSelectionListener(this);
		
		this.addressTrasherModel.addObserver(this.addressTrasherContainer.staticMap);
		this.addressTrasherModel.addObserver(this.addressTrasherContainer);
		this.addressTrasherModel.setAddresses(addresses);
		
		this.addressTrasherContainer.jbNo.addActionListener(this);
		this.addressTrasherContainer.jbYes.addActionListener(this);
	}
	
	public void addTrashedAddressListener(TrashedAddressListener listener) {
		this.listeners.add(TrashedAddressListener.class, listener);
	}
	
	public void removeTrashedAddressListener(TrashedAddressListener listener) {
		this.listeners.remove(TrashedAddressListener.class, listener);
	}
	
	public TrashedAddressListener[] getTrashedAddressListener() {
		return this.listeners.getListeners(TrashedAddressListener.class);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(this.addressTrasherContainer.listAddress) && !e.getValueIsAdjusting()) {
			this.addressTrasherModel.setAddressSelected(this.addressTrasherContainer.listAddress.getSelectedValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.addressTrasherContainer.jbNo)) {
			this.addressTrasherContainer.dispose();
		}
		
		if(e.getSource().equals(this.addressTrasherContainer.jbYes)) {
			for(TrashedAddressListener listener : this.getTrashedAddressListener()) {
				listener.TrashedAddress(new TrashedAddressEvent(this, this.addressTrasherModel.getAddresses()));
			}
			this.addressTrasherContainer.dispose();
		}
	}
}
