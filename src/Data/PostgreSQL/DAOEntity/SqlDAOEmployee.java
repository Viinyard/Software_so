package Data.PostgreSQL.DAOEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.AbstractDAOFactory;
import Data.DAO;
import Entity.Employee;

public class SqlDAOEmployee extends DAO<Employee> {

	public SqlDAOEmployee(Connection connect) {
		super(connect);
	}

	@Override
	public long create(Employee obj) {
		PreparedStatement ps = null;
		String query = "INSERT INTO "+this.getTableName()+" (firstName, lastName, gender, birthDate, licenceNumber, licenceDate, mail) values (?, ?, ?, ?, ?, ?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getGender());
			ps.setDate(4, new java.sql.Date(obj.getBirthDate()));
			ps.setString(5, obj.getLicenceNumber());
			ps.setDate(6, new java.sql.Date(obj.getLicenceDate()));
			ps.setString(7, obj.getMail());
			
			ps.executeUpdate();
			
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					obj.setId(generatedKeys.getLong("id"));
				} else {
					throw new SQLException("Creating "+obj.toString()+" failed, no ID obtained.");
				}
			}
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return obj.getId();
	}

	@Override
	public boolean delete(Employee obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "DELETE FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, obj.getId());
			
			res = ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public boolean update(Employee obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "UPDATE "+this.getTableName()+" SET firstName = ?, lastName = ?, gender = ?, birthDate = ?, licenceNumber = ?, licenceDate = ?, mail = ? WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getGender());
			ps.setDate(4, new java.sql.Date(obj.getBirthDate()));
			ps.setString(5, obj.getLicenceNumber());
			ps.setDate(6, new java.sql.Date(obj.getLicenceDate()));
			ps.setString(7, obj.getMail());
			
			res = ps.executeUpdate() > 0;
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public Employee find(long id) {
		PreparedStatement ps = null;
		Employee emp = null;
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				emp = new Employee(
						rs.getLong("id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("gender"),
						rs.getDate("birthDate").getTime(),
						rs.getString("licenceNumber"),
						rs.getDate("licenceDate").getTime(),
						rs.getString("mail"));
				emp.addPhoneNumbers(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberEmployeeDAO().find(emp));
				emp.addAddresses(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressEmployeeDAO().find(emp));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	protected String getTableName() {
		return "Employee";
	}

	@Override
	public ArrayList<Employee> findAll() {
		PreparedStatement ps = null;
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		String query = "SELECT * FROM "+this.getTableName()+";";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee emp = new Employee(
						rs.getLong("id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("gender"),
						rs.getDate("birthDate").getTime(),
						rs.getString("licenceNumber"),
						rs.getDate("licenceDate").getTime(),
						rs.getString("mail"));
				emp.addPhoneNumbers(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberEmployeeDAO().find(emp));
				emp.addAddresses(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressEmployeeDAO().find(emp));
				
				employees.add(emp);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return employees;
	}
}
