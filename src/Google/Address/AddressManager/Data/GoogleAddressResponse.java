package Google.Address.AddressManager.Data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAddressResponse {
	
	public static final String OK = "OK";
	public static final String NO_RESULT = "ZERO_RESULTS";

	private Result[] results;
	private Result[] predictions;
	private String status;
	private String error_message;
	
	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Result[] getPredictions() {
		return predictions;
	}

	public void setPredictions(Result[] predictions) {
		this.predictions = predictions;
	}
}