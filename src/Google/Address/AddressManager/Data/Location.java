package Google.Address.AddressManager.Data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	private String lat;

	private String lng;
	
	public Location(String latLng) {
		super();
		String[] tmp = latLng.split(",");
		if(tmp.length == 2) {
			this.setLat(tmp[0]);
			this.setLng(tmp[1]);
		}
		
		if(tmp.length != 2 || !latLng.equals(this.toString())) {
			System.out.println("Wrong LatLng String input");
		}
	}
	
	public Location() {}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String toString() {
		return this.lat+","+this.lng;
	}
}
