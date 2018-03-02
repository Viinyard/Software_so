package Google.Address.AddressManager.Trash;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import Entity.Address;

public class ListAddressModel extends AbstractListModel<Address> {

	private static final long serialVersionUID = 1L;

	private ArrayList<Address> addresses;
	
	public ListAddressModel(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	
	@Override
	public int getSize() {
		return this.addresses.size();
	}

	@Override
	public Address getElementAt(int index) {
		return this.addresses.get(index);
	}
}
