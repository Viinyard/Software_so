package Google.Address.AddressManager.Data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GooglePlaceResponse {
	
	public static final String OK = "OK";
	public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
	public static final String ZERO_RESULTS = "ZERO_RESULTS";
	public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
	public static final String REQUEST_DENIED = "REQUEST_DENIED";
	public static final String INVALID_REQUEST = "INVALID_REQUEST";
	public static final String NOT_FOUND = "NOT_FOUND";
	
	private PlaceDetailsResult result;
	
	private String status;
	
	public PlaceDetailsResult getResult() {
		return result;
	}

	public void setResult(PlaceDetailsResult result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
