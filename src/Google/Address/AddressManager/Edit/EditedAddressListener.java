package Google.Address.AddressManager.Edit;

import java.util.EventListener;

public interface EditedAddressListener extends EventListener {
	
	public void addressEdited(EditedAddressEvent e);

}
