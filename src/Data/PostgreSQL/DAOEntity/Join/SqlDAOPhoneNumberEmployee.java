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
import Entity.PhoneNumber;

public class SqlDAOPhoneNumberEmployee extends DAOJoin<PhoneNumber, Employee> {

	public SqlDAOPhoneNumberEmployee(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "PhoneNumberEmployee";
	}

	@Override
	public boolean create(PhoneNumber obj, Employee owner) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberDAO().create(obj);
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "INSERT INTO "+this.getTableName()+" (idEmployee, idPhoneNumber) values (?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			ps.setLong(2, obj.getId());
			
			res = ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public ArrayList<PhoneNumber> find(Employee owner) {
		PreparedStatement ps = null;
		ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE idEmployee = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				phones.add(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberDAO().find(rs.getLong("idEmployee")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return phones;
	}

}
