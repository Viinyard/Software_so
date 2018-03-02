package Entity;

public class PhoneNumber {
	
	private long id;
	private String phoneNumber;
	private String phoneType;
	
	public PhoneNumber(long id, String phoneNumber, String phoneType) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.phoneType = phoneType;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
}
