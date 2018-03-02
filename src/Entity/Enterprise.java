package Entity;

import java.util.ArrayList;

import Data.AbstractDAOFactory;

public class Enterprise {

	private long id;
	private String label;
	private long creationDate;
	private long taxYear;
	private String siretNumber;
	
	private ArrayList<Attachment> attachments;
	private ArrayList<Address> addresses;
	private ArrayList<PhoneNumber> phoneNumbers;
	private ArrayList<Employee> employees;
	
	public Enterprise(long id, String label, long creationDate, long taxYear, String siretNumber) {
		super();
		this.id = id;
		this.label = label;
		this.creationDate = creationDate;
		this.taxYear = taxYear;
		this.siretNumber = siretNumber;
		
		this.attachments = new ArrayList<Attachment>();
		this.addresses = new ArrayList<Address>();
		this.phoneNumbers = new ArrayList<PhoneNumber>();
		this.employees = new ArrayList<Employee>();
	}

	public void addAttachment(Attachment attachment) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentEnterpriseDAO().create(attachment, this);
		this.attachments.add(attachment);
	}
	
	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	
	public void addPhoneNumber(PhoneNumber phone) {
		this.phoneNumbers.add(phone);
	}
	
	public void addEmployee(Employee emp) {
		this.employees.add(emp);
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
	
	public void removeEmployee(Employee emp) {
		this.employees.remove(emp);
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
	
	public ArrayList<Employee> getEmployees() {
		return this.employees;
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
	
	public void addEmployees(ArrayList<Employee> employees) {
		this.employees.addAll(employees);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(long taxYear) {
		this.taxYear = taxYear;
	}

	public String getSiretNumber() {
		return siretNumber;
	}

	public void setSiretNumber(String siretNumber) {
		this.siretNumber = siretNumber;
	}
}
