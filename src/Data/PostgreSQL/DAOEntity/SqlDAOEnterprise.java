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
import Entity.Enterprise;

public class SqlDAOEnterprise extends DAO<Enterprise> {

	public SqlDAOEnterprise(Connection connect) {
		super(connect);
	}

	@Override
	public long create(Enterprise obj) {
		PreparedStatement ps = null;
		String query = "INSERT INTO "+this.getTableName()+" (label, creationDate, taxYear, siretNumber) values (?, ?, ?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getLabel());
			ps.setDate(2, new java.sql.Date(obj.getCreationDate()));
			ps.setDate(3, new java.sql.Date(obj.getTaxYear()));
			ps.setString(5, obj.getSiretNumber());
			
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
	public boolean delete(Enterprise obj) {
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
	public boolean update(Enterprise obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "UPDATE "+this.getTableName()+" SET label = ?, creationDate = ?, taxYear = ?, siretNumber = ? WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getLabel());
			ps.setDate(2, new java.sql.Date(obj.getCreationDate()));
			ps.setDate(3, new java.sql.Date(obj.getTaxYear()));
			ps.setString(5, obj.getSiretNumber());
			
			res = ps.executeUpdate() > 0;
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public Enterprise find(long id) {
		PreparedStatement ps = null;
		Enterprise ent = null;
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ent = new Enterprise(
						rs.getLong("id"),
						rs.getString("label"),
						rs.getDate("creationDate").getTime(),
						rs.getDate("taxYear").getTime(),
						rs.getString("siretNumber"));
				ent.addAttachments(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentEnterpriseDAO().find(ent));
				ent.addAddresses(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressEnterpriseDAO().find(ent));
				ent.addPhoneNumbers(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberEnterpriseDAO().find(ent));
				ent.addEmployees(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEmployeeEnterpriseDAO().find(ent));
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return ent;
	}

	@Override
	protected String getTableName() {
		return "Enterprise";
	}

	@Override
	public ArrayList<Enterprise> findAll() {
		PreparedStatement ps = null;
		ArrayList<Enterprise> enterprises = new ArrayList<Enterprise>();
		
		String query = "SELECT * FROM "+this.getTableName()+";";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Enterprise ent = new Enterprise(
						rs.getLong("id"),
						rs.getString("label"),
						rs.getDate("creationDate").getTime(),
						rs.getDate("taxYear").getTime(),
						rs.getString("siretNumber"));
				ent.addAttachments(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentEnterpriseDAO().find(ent));
				ent.addAddresses(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAddressEnterpriseDAO().find(ent));
				ent.addPhoneNumbers(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getPhoneNumberEnterpriseDAO().find(ent));
				ent.addEmployees(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getEmployeeEnterpriseDAO().find(ent));
				
				enterprises.add(ent);
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return enterprises;
	}
}
