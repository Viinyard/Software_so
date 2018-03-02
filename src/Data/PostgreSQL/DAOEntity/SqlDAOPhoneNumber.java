package Data.PostgreSQL.DAOEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.DAO;
import Entity.PhoneNumber;

public class SqlDAOPhoneNumber extends DAO<PhoneNumber> {

	public SqlDAOPhoneNumber(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "PhoneNumber";
	}

	@Override
	public long create(PhoneNumber obj) {
		PreparedStatement ps = null;
		
		String query = "INSERT INTO "+this.getTableName()+" (phoneNumber, phoneType) values (?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getPhoneNumber());
			ps.setString(2, obj.getPhoneType());
			
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
	public boolean delete(PhoneNumber obj) {
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
	public boolean update(PhoneNumber obj) {
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "UPDATE "+this.getTableName()+" SET phoneNumber = ?, phoneType = ? WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getPhoneNumber());
			ps.setString(2, obj.getPhoneType());
			ps.setLong(3, obj.getId());
			
			res = ps.executeUpdate() > 0;
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public PhoneNumber find(long id) {
		PreparedStatement ps = null;
		PhoneNumber phone = null;
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				phone = new PhoneNumber(
						rs.getLong("id"),
						rs.getString("phoneNumber"),
						rs.getString("phoneType"));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return phone;
	}

	@Override
	public ArrayList<PhoneNumber> findAll() {
		PreparedStatement ps = null;
		ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
		
		String query = "SELECT * FROM "+this.getTableName()+";";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				phones.add(new PhoneNumber(
						rs.getLong("id"),
						rs.getString("phoneNumber"),
						rs.getString("phoneType")));
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
