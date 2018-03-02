package Google.Address.AddressManager;

import java.util.ArrayList;

import Component.CustomTable.ColumnObject;
import Component.CustomTable.JCustomTableAbstractTableModel;
import Entity.Address;

public class AddressTableModel extends JCustomTableAbstractTableModel {

	private static final long serialVersionUID = 1L;

	private ArrayList<Address> addresses;
	
	public AddressTableModel() {
		super(new ColumnObject[] {
				new ColumnObject("id", Long.class, false),
				new ColumnObject("label", String.class, true),
				new ColumnObject("streetNumber", String.class, true),
				new ColumnObject("street", String.class, true),
				new ColumnObject("zipCode", Integer.class, false),
				new ColumnObject("city", String.class, false),
				new ColumnObject("country", String.class, false),
				new ColumnObject("state", String.class, false),
				new ColumnObject("location", String.class, false),
				new ColumnObject("url", String.class, false),
				new ColumnObject("formatted_address", String.class, true),
				new ColumnObject("place_id", String.class, false)
		});
		this.addresses = new ArrayList<Address>();
	}
	
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
		this.fireTableDataChanged();
	}
	
	public void addAddress(Address address) {
		this.addresses.add(address);
		this.fireTableRowsInserted(this.addresses.size()-1, this.addresses.size()-1);
	}
	
	public void removeAddresses(ArrayList<Address> address) {
		this.addresses.removeAll(address);
		this.fireTableDataChanged();
	}

	public Address getAddressAt(int rowIndex) {
		return rowIndex >= 0 ? this.addresses.get(rowIndex) : null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.columns_displayed[columnIndex].getClass();
	}
	
	@Override
	public int getRowCount() {
		return this.addresses.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (this.columns_displayed[columnIndex].getColomn_name()) {
		case "id":
			return this.addresses.get(rowIndex).getId(); 
		case "label":
			return this.addresses.get(rowIndex).getLabel();
		case "streetNumber":
			return this.addresses.get(rowIndex).getStreetNumber();
		case "street":
			return this.addresses.get(rowIndex).getStreet();
		case "zipCode":
			return this.addresses.get(rowIndex).getZipCode();
		case "city":
			return this.addresses.get(rowIndex).getCity();
		case "country":
			return this.addresses.get(rowIndex).getCountry();
		case "state":
			return this.addresses.get(rowIndex).getState();
		case "location":
			return this.addresses.get(rowIndex).getLocation();
		case "url":
			return this.addresses.get(rowIndex).getUrl();
		case "formatted_address":
			return this.addresses.get(rowIndex).getFormatted_address();
		case "place_id":
			return this.addresses.get(rowIndex).getPlace_id();
		default:
			throw new IllegalArgumentException();
		}
	}
}
