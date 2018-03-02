package Google.Address.AddressManager.Trash;

import java.util.ArrayList;
import java.util.Observable;

import Entity.Address;

public class AddressTrasherModel extends Observable {
	
	public ArrayList<Address> addresses;
	
	public void setAddressSelected(Address address) {
		this.setChanged();
		this.notifyObservers(address);
	}
	
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
		this.setChanged();
		this.notifyObservers(addresses);
	}
	
	public ArrayList<Address> getAddresses() {
		return this.addresses;
	}
}
