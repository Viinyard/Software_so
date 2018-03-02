package Google.Address.AddressManager.Data;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResult extends Result {
	
	private String name;
	private String vicinity;
	private String url;
	
	@JsonIgnore
	private String adr_address;
	
	@Override
	public String toString() {
		return super.toString()+"\n"
				+ "name=" + name + ",\n"
				+ "url=" + url + ",\n"
				+ "vicinity=" + vicinity + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
}
