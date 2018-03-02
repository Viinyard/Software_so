package Google.Address.AddressManager.Edit;

import java.util.Observable;

import Entity.Address;

public class AddressEditorModel extends Observable {
	
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		this.setChanged();
		this.notifyObservers(this.address);
	}
	
	public void setDocumentChanged() {
		this.setChanged();
		this.notifyObservers(true);
	}
}
