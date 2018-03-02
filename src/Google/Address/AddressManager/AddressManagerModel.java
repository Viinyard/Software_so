package Google.Address.AddressManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Entity.Address;

public class AddressManagerModel extends Observable {

	private ArrayList<Address> addressesSelected;
	
	private Address address;
	
	public AddressManagerModel() {
		this.addressesSelected = new ArrayList<Address>();
	}
	
	public ArrayList<Address> getAddressesSelected() {
		return this.addressesSelected;
	}

	public void setAddressesSelected(ArrayList<Address> addressesSelected) {
		this.addressesSelected = addressesSelected;
		this.setChanged();
		this.notifyObservers(this.addressesSelected);
	}
	
	public void clearAddressesSelected() {
		if(this.addressesSelected != null) {
			this.addressesSelected.clear();
			this.setChanged();
			this.notifyObservers(this.addressesSelected);
		}
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, null);
	}

	public void setAddress(Address address) {
		this.address = address;
		this.setChanged();
		this.notifyObservers(this.address);
	}
}
