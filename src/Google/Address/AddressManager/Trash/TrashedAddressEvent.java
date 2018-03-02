package Google.Address.AddressManager.Trash;

import java.util.ArrayList;
import java.util.EventObject;

import Entity.Address;

public class TrashedAddressEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Address> trashedAddresses;

	public TrashedAddressEvent(Object source, ArrayList<Address> trashedAddresses) {
		super(source);
		this.trashedAddresses = trashedAddresses;
	}
	
	public ArrayList<Address> getTrashedAddresses() {
		return this.trashedAddresses;
	}
}
