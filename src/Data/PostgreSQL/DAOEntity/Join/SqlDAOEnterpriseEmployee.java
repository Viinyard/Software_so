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

public class SqlDAOEnterpriseEmployee extends DAOJoin<Enterprise, Employee> {

	public SqlDAOEnterpriseEmployee(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "EmployeeEnterprise";
	}

	@Override
	public boolean create(Enterprise obj, Employee owner) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEnterpriseDAO().create(obj);
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
	public ArrayList<Enterprise> find(Employee owner) {
		PreparedStatement ps = null;
		ArrayList<Enterprise> enterprises = new ArrayList<Enterprise>();
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE idEmployee = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				enterprises.add(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEnterpriseDAO().find(rs.getLong("idEnterprise")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return null;
	}

}
