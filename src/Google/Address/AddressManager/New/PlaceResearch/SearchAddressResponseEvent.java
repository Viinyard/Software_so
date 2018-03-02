package Google.Address.AddressManager.New.PlaceResearch;

import java.awt.image.BufferedImage;
import java.util.EventObject;

import Entity.Address;

public class SearchAddressResponseEvent extends EventObject {
	
	@Override
	public String toString() {
		return "SearchAddressResponseEvent [address=" + address + ", image=" + image + "]";
	}

	private static final long serialVersionUID = 1L;

	private Address address;
	private BufferedImage image;

	public SearchAddressResponseEvent(Object source, Address address, BufferedImage image) {
		super(source);
		this.address = address;
		this.image = image;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
}
