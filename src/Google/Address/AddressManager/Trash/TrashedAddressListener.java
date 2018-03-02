package Google.Address.AddressManager.Trash;

import java.util.EventListener;

public interface TrashedAddressListener extends EventListener { 

	public void TrashedAddress(TrashedAddressEvent e);
	
}
