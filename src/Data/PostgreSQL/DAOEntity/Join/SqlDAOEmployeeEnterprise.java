package Data.PostgreSQL.DAOEntity.Join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.AbstractDAOFactory;
import Data.DAOJoin;
import Entity.Employee;
import Entity.Enterprise;

public class SqlDAOEmployeeEnterprise extends DAOJoin<Employee, Enterprise> {

	public SqlDAOEmployeeEnterprise(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "EmployeeEnterprise";
	}

	@Override
	public boolean create(Employee obj, Enterprise owner) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEmployeeDAO().create(obj);
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "INSERT INTO "+this.getTableName()+" (idEmployee, idEnterprise) values (?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, obj.getId());
			ps.setLong(2, owner.getId());
			
			res = ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public ArrayList<Employee> find(Enterprise owner) {
		PreparedStatement ps = null;
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE idEnterprise = ?;";
		
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				employees.add(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEmployeeDAO().find(rs.getLong("idEmployee")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		for(Employee emp : employees) {
			emp.addEnterprise(owner);
		}
		
		return employees;
	}
}
