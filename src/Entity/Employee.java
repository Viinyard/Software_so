package Entity;

import java.util.ArrayList;

import Data.AbstractDAOFactory;

public class Employee {

	private long id;
	private String firstName;
	private String lastName;
	private String gender;
	private long birthDate;
	private String licenceNumber;
	private long licenceDate;
	private String mail;
	
	private ArrayList<Attachment> attachments;
	private ArrayList<PhoneNumber> phoneNumbers;
	private ArrayList<Address> addresses;
	private ArrayList<Enterprise> enterprises;
	
	public Employee(long id, String firstName, String lastName, String gender, long birthDate, String licenceNumber,
			long licenceDate, String mail) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.licenceNumber = licenceNumber;
		this.licenceDate = licenceDate;
		this.mail = mail;
		
		this.attachments = new ArrayList<Attachment>();
		this.addresses = new ArrayList<Address>();
		this.phoneNumbers = new ArrayList<PhoneNumber>();
		this.enterprises = new ArrayList<Enterprise>();
	}
	
	public void addAttachment(Attachment attachment) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentEmployeeDAO().create(attachment, this);
		this.attachments.add(attachment);
	}
	
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	
	public void addPhoneNumber(PhoneNumber phone) {
		this.phoneNumbers.add(phone);
	}
	
	public void addEnterprise(Enterprise ent) {
		this.enterprises.add(ent);
	}
	
	public void removeAttachment(Attachment attachment) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentDAO().delete(attachment);
		this.attachments.remove(attachment);
	}
	
	public void removeAddress(Address address) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().delete(address);
		this.addresses.remove(address);
	}
	
	public void removePhoneNumber(PhoneNumber phone) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberDAO().delete(phone);
		this.phoneNumbers.remove(phone);
	}
	
	public void removeEnterprise(Enterprise ent) {
		this.enterprises.add(ent);
	}
	
	public ArrayList<Attachment> getAttachments() {
		return this.attachments;
	}
	
	public ArrayList<Address> getAddresses() {
		return this.addresses;
	}
	
	public ArrayList<PhoneNumber> getPhoneNumbers() {
		return this.phoneNumbers;
	}
	
	public ArrayList<Enterprise> getEnterprises() {
		return this.enterprises;
	}
	
	public void addAttachments(ArrayList<Attachment> attachments) {
		this.attachments.addAll(attachments);
	}
	
	public void addAddresses(ArrayList<Address> addresses) {
		this.addresses.addAll(addresses);
	}
	
	public void addPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
		this.phoneNumbers.addAll(phoneNumbers);
	}
	
	public void addEnterprises(ArrayList<Enterprise> enterprises) {
		this.enterprises.addAll(enterprises);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public long getLicenceDate() {
		return licenceDate;
	}

	public void setLicenceDate(long licenceDate) {
		this.licenceDate = licenceDate;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}