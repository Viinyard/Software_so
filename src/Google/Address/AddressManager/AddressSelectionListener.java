package Google.Address.AddressManager;

import java.util.EventListener;

public interface AddressSelectionListener extends EventListener {
	public void AddressSelectionChange(AddressSelectionEvent e);
}
