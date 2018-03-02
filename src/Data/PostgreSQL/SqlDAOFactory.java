package Data.PostgreSQL;

import java.sql.Connection;

import Data.AbstractDAOFactory;
import Data.DAO;
import Data.DAOJoin;
import Data.PostgreSQL.DAOEntity.SqlDAOAddress;
import Data.PostgreSQL.DAOEntity.SqlDAOAttachment;
import Data.PostgreSQL.DAOEntity.SqlDAOCar;
import Data.PostgreSQL.DAOEntity.SqlDAOEmployee;
import Data.PostgreSQL.DAOEntity.SqlDAOEnterprise;
import Data.PostgreSQL.DAOEntity.SqlDAOPhoneNumber;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAddressEmployee;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAddressEnterprise;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAttachmentAddress;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAttachmentCar;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAttachmentEmployee;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOAttachmentEnterprise;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOEmployeeEnterprise;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOEnterpriseEmployee;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOPhoneNumberEmployee;
import Data.PostgreSQL.DAOEntity.Join.SqlDAOPhoneNumberEnterprise;
import Entity.Address;
import Entity.Attachment;
import Entity.Car;
import Entity.Employee;
import Entity.Enterprise;
import Entity.PhoneNumber;

public class SqlDAOFactory extends AbstractDAOFactory {
	protected static final Connection connect = PgSqlConnection.getInstance();

	@Override
	public DAO<Address> getAddressDAO() {
		return new SqlDAOAddress(SqlDAOFactory.connect);
	}

	public DAO<Employee> getEmployeeDAO() {
		return new SqlDAOEmployee(SqlDAOFactory.connect);
	}

	@Override
	public DAO<Enterprise> getEnterpriseDAO() {
		return new SqlDAOEnterprise(SqlDAOFactory.connect);
	}

	@Override
	public DAO<Attachment> getAttachmentDAO() {
		return new SqlDAOAttachment(SqlDAOFactory.connect);
	}

	@Override
	public DAO<Car> getCarDAO() {
		return new SqlDAOCar(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Attachment, Enterprise> getAttachmentEnterpriseDAO() {
		return new SqlDAOAttachmentEnterprise(SqlDAOFactory.connect);
	}

	@Override
	public DAO<PhoneNumber> getPhoneNumberDAO() {
		return new SqlDAOPhoneNumber(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<PhoneNumber, Enterprise> getPhoneNumberEnterpriseDAO() {
		return new SqlDAOPhoneNumberEnterprise(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Address, Enterprise> getAddressEnterpriseDAO() {
		return new SqlDAOAddressEnterprise(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Attachment, Car> getAttachmentCarDAO() {
		return new SqlDAOAttachmentCar(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<PhoneNumber, Employee> getPhoneNumberEmployeeDAO() {
		return new SqlDAOPhoneNumberEmployee(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Address, Employee> getAddressEmployeeDAO() {
		return new SqlDAOAddressEmployee(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Attachment, Employee> getAttachmentEmployeeDAO() {
		return new SqlDAOAttachmentEmployee(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Employee, Enterprise> getEmployeeEnterpriseDAO() {
		return new SqlDAOEmployeeEnterprise(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Enterprise, Employee> getEnterpriseEmployeeDAO() {
		return new SqlDAOEnterpriseEmployee(SqlDAOFactory.connect);
	}

	@Override
	public DAOJoin<Attachment, Address> getAttachmentAddressDAO() {
		return new SqlDAOAttachmentAddress(SqlDAOFactory.connect);
	}
}
