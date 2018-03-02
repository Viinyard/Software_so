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
import Entity.Address;
import Entity.Employee;

public class SqlDAOAddressEmployee extends DAOJoin<Address, Employee> {

	public SqlDAOAddressEmployee(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "AddressEmployee";
	}

	@Override
	public boolean create(Address obj, Employee owner) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().create(obj);
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "INSERT INTO "+this.getTableName()+" (idEmployee, idAddress) values (?, ?);";
		
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
	public ArrayList<Address> find(Employee owner) {
		PreparedStatement ps = null;
		ArrayList<Address> addresses = new ArrayList<Address>();
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE idEmployee = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				addresses.add(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressDAO().find(rs.getLong("idAddress")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return addresses;
	}
}
