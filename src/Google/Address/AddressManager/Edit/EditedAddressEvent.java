package Google.Address.AddressManager.Edit;

import java.util.EventObject;

import Entity.Address;

public class EditedAddressEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private Address address;

	public EditedAddressEvent(Object source, Address address) {
		super(source);
		this.address = address;
	}

	public Address getAddress() {
		return this.address;
	}
}
