package Google.Address.AddressManager.New;

import Google.Address.AddressManager.New.AddressResearch.AddressReseacher;
import Google.Address.AddressManager.New.AddressResearch.ResultSelectionEvent;
import Google.Address.AddressManager.New.AddressResearch.ResultSelectionListener;
import Google.Address.AddressManager.New.PlaceResearch.PlaceDetailsLoader;
import Google.Address.AddressManager.New.PlaceResearch.SearchAddressResponseListener;

public class AddressMaker implements ResultSelectionListener {
	
	private PlaceDetailsLoader placeDetailsLoader;
	private AddressReseacher addressReseacher;
	
	public AddressMaker() {
		this.addressReseacher = new AddressReseacher();
		this.addressReseacher.addResultSelectionListener(this);
		
		this.placeDetailsLoader = new PlaceDetailsLoader();
	}
	
	public void addSearchAddressResponseListener(SearchAddressResponseListener listener) {
		this.placeDetailsLoader.addSearchAddressListener(listener);
	}

	public void removeSearchAddressResponseListener(SearchAddressResponseListener listener) {
		this.placeDetailsLoader.removeSearchAddressListener(listener);
	}
	
	public SearchAddressResponseListener[] getSearchAddressResponseListener() {
		return this.placeDetailsLoader.getSearchAddressListener();
	}
	
	@Override
	public void resultChange(ResultSelectionEvent e) {
		this.placeDetailsLoader.setResult(e.getSelectedResult());
	}
}
