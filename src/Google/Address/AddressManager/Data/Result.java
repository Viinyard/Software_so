package Google.Address.AddressManager.Data;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	private String formatted_address;
	private Geometry geometry;
	private AddressComponent[] address_components;
	private String[] types;
	private String[] matched_substrings;
	private String reference;
	private String[] terms;
	private String description;
	private String id;
	private boolean partial_match;

	@JsonIgnore
	private int postcode_localities;
	
	@JsonIgnore
	private String place_id;
	
	@JsonSetter("place_id")
	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public boolean isPartial_match() {
		return partial_match;
	}

	public void setPartial_match(boolean partial_match) {
		this.partial_match = partial_match;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public AddressComponent[] getAddress_components() {
		return address_components;
	}

	public void setAddress_components(AddressComponent[] address_components) {
		this.address_components = address_components;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getMatched_substrings() {
		return matched_substrings;
	}

	public void setMatched_substrings(String[] matched_substrings) {
		this.matched_substrings = matched_substrings;
	}

	public String getReference() {
		return reference;
	}

	@Override
	public String toString() {
		return "Result [formatted_address=" + formatted_address + ",\n"
				+ "partial_match=" + partial_match + ",\n"
				+ "geometry=" + geometry + ",\n"
				+ "address_components=" + Arrays.toString(address_components)+ ",\n"
				+ "postcode_localities=" + postcode_localities + ",\n"
				+ "types=" + types + ",\n"
				+ "matched_substrings=" + Arrays.toString(matched_substrings) + ",\n"
				+ "reference=" + reference + ",\n"
				+ "terms=" + Arrays.toString(terms) + ",\n"
				+ "description=" + description + ",\n"
				+ "id=" + id + ",\n"
				+ "place_id=" + place_id + "]";
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String[] getTerms() {
		return terms;
	}

	public void setTerms(String[] terms) {
		this.terms = terms;
	}

}