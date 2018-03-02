package Google.Address.AddressManager;

import java.util.EventObject;

public class AddressSelectionEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private int selectedRowIndex;
	private int[] selectedRowsIndex;

	public AddressSelectionEvent(Object source, int selectedRowIndex, int[] selectedRowsIndex) {
		super(source);
		this.selectedRowIndex = selectedRowIndex;
		this.selectedRowsIndex = selectedRowsIndex;
	}
	
	public int getSelectedAddressCount() {
		return this.selectedRowsIndex.length;
	}
	
	public int[] getAddressesSelected() {
		return this.selectedRowsIndex;
	}
	
	public int getAddressSelected() {
		return this.selectedRowIndex;
	}
}
