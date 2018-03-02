package Google.Address.AddressManager.New.PlaceResearch;

import java.util.EventListener;

public interface SearchAddressResponseListener extends EventListener {
	public void findAddress(SearchAddressResponseEvent e);
}
