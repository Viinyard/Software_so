package Entity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import Data.AbstractDAOFactory;
import Google.Address.AddressManager.Data.AddressComponent;
import Google.Address.AddressManager.Data.GoogleAddressResponse;
import Google.Address.AddressManager.Data.GooglePlaceResponse;
import Google.Address.AddressManager.Data.Location;
import Google.Address.AddressManager.Data.Result;

public class Address {
	
	private final String URL = "http://maps.googleapis.com/maps/api/geocode/json";
	
	private long id;
	private String label;
	private String streetNumber;
	private String street;
	private int zipCode;
	private String city;
	private String country;
	private String state;
	private Location location;
	private String url;
	private String formatted_address;
	private String place_id;
	
	private ArrayList<Attachment> attachments;
	
	public Address() {
		this.attachments = new ArrayList<Attachment>();
	}

	public Address(long id, String label, String streetNumber, String street, int zipCode, String city,
			String country, String state, Location location, String place_id, String url, String formatted_address) {
		this();
		this.id = id;
		this.label = label;
		this.streetNumber = streetNumber;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		this.state = state;
		this.location = location;
		this.place_id = place_id;
		this.url = url;
		this.formatted_address = formatted_address;
	}
	
	public Address(GooglePlaceResponse resp) {
		this();
		if(resp != null && resp.getResult() != null && resp.getResult().getAddress_components() != null) {
			for(AddressComponent ac : resp.getResult().getAddress_components()) {
				if(ac.getTypes().length > 0) {
					switch(ac.getTypes()[0]) {
					case "postal_code":
						this.zipCode = Integer.parseInt(ac.getLong_name());
						break;
					case "country":
						this.country = ac.getLong_name();
						break;
					case "administrative_area_level_1":
						this.state = ac.getLong_name();
						break;
					case "locality":
						this.city = ac.getLong_name();
						break;
					case "route":
						this.street = ac.getLong_name();
						break;
					case "street_number":
						this.streetNumber = ac.getLong_name();
						break;
					}
				}
			}
			
			this.location = resp.getResult().getGeometry().getLocation();
			this.formatted_address = resp.getResult().getFormatted_address();
			this.url = resp.getResult().getUrl();
			
			try {
				GoogleAddressResponse addressResponse = this.getFromLatLng(resp.getResult().getGeometry().getLocation());
				if(addressResponse.getResults().length > 0 && addressResponse.getResults()[0].getAddress_components() != null) {
					for(Result r : addressResponse.getResults()) {
						for(AddressComponent ac : r.getAddress_components()) {
							if (ac.getTypes()[0].equals("administrative_area_level_1")) {
								this.state = ac.getLong_name();
							} else if(this.city == null && ac.getTypes()[0].equals("locality")) {
								this.city = ac.getLong_name();
							} else if(this.street == null && ac.getTypes()[0].equals("route")) {
								this.street = ac.getLong_name();
							}
						}
						if(this.state != null && this.city != null && this.street != null) {
							break;
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String[] formattedAddress = resp.getResult().getFormatted_address().split(", ");
			for(String s : formattedAddress) {
				if(!((this.street!= null && s.contains(this.street)) || 
						((this.city != null && s.contains(this.city)) && s.contains(Integer.toString(this.zipCode))) 
						|| (this.country != null && s.contains(this.country)))) {
					this.streetNumber = s;
				}
			}
			
			this.label = resp.getResult().getName();
			this.place_id = resp.getResult().getPlace_id();
		}
	}
	
	@Override
	public String toString() {
		String labStr = "";
		if(this.label != null) {
			labStr = this.label+", ";
		}
		
		String streetStr = "";
		if(this.streetNumber != null && this.streetNumber.length() > 0) {
			streetStr = this.streetNumber+" ";
		}
		
		return labStr+streetStr+this.street+", "+this.zipCode+" "+this.city+", "+this.country;
	}
	
	public GoogleAddressResponse getFromLatLng(Location location) throws IOException {
		URL url = new URL(this.URL + "?latlng=" + location.toString() + "&sensor=true");
		
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		
		GoogleAddressResponse response = (GoogleAddressResponse) mapper.readValue(in, GoogleAddressResponse.class);
		
		in.close();
		
		return response;
	}
	
	public GoogleAddressResponse getFromPlaceId(String place_id) throws IOException {
		URL url = new URL(this.URL + "?place_id=" + place_id + "&sensor=true");
		
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		
		GoogleAddressResponse response = (GoogleAddressResponse) mapper.readValue(in, GoogleAddressResponse.class);
		
		in.close();
		
		return response;
	}
	
	public void addAttachment(Attachment attachment) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentAddressDAO().create(attachment, this);
		this.attachments.add(attachment);
	}

	public void removeAttachment(Attachment attachment) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentDAO().delete(attachment);
		this.attachments.remove(attachment);
	}
	
	public void removeAllAttachment() {
		for(Attachment attachment : this.attachments) {
			AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentDAO().delete(attachment);
		}
		
		this.attachments.clear();
	}
	
	public ArrayList<Attachment> getAttachments() {
		return this.attachments;
	}
	
	public void addAttachments(ArrayList<Attachment> attachments) {
		this.attachments.addAll(attachments);
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
}
