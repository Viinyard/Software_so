package Data;

import Data.PostgreSQL.SqlDAOFactory;
import Entity.Address;
import Entity.Attachment;
import Entity.Car;
import Entity.Employee;
import Entity.Enterprise;
import Entity.PhoneNumber;

public abstract class AbstractDAOFactory {
	public static final int SQL = 0;
	
	public abstract DAO<Address> getAddressDAO();
	
	public abstract DAO<Enterprise> getEnterpriseDAO();
	
	public abstract DAO<Attachment> getAttachmentDAO();
	
	public abstract DAO<Employee> getEmployeeDAO();
	
	public abstract DAO<Car> getCarDAO();
	
	public abstract DAO<PhoneNumber> getPhoneNumberDAO();
	
	public abstract DAOJoin<PhoneNumber, Enterprise> getPhoneNumberEnterpriseDAO();
	
	public abstract DAOJoin<PhoneNumber, Employee> getPhoneNumberEmployeeDAO();
	
	public abstract DAOJoin<Attachment, Enterprise> getAttachmentEnterpriseDAO();
	
	public abstract DAOJoin<Attachment, Employee> getAttachmentEmployeeDAO();
	
	public abstract DAOJoin<Attachment, Car> getAttachmentCarDAO();
	
	public abstract DAOJoin<Attachment, Address> getAttachmentAddressDAO();
	
	public abstract DAOJoin<Address, Enterprise> getAddressEnterpriseDAO();
	
	public abstract DAOJoin<Address, Employee> getAddressEmployeeDAO();
	
	public abstract DAOJoin<Employee, Enterprise> getEmployeeEnterpriseDAO();
	
	public abstract DAOJoin<Enterprise, Employee> getEnterpriseEmployeeDAO();
	
	public static AbstractDAOFactory getFactory(int DAO) {
		switch(DAO) {
		case AbstractDAOFactory.SQL :
			return new SqlDAOFactory();
		default :
			return null;
		}
	}

}
