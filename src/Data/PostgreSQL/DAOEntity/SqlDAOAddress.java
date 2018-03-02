package Data.PostgreSQL.DAOEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.AbstractDAOFactory;
import Data.DAO;
import Entity.Address;
import Google.Address.AddressManager.Data.Location;

/**
 * @author VinYarD
 *
 */
public class SqlDAOAddress extends DAO<Address> {

	public SqlDAOAddress(Connection connect) {
		super(connect);
	}

	@Override
	public long create(Address obj) {
		PreparedStatement ps = null;
		String query = "INSERT INTO " + this.getTableName()
				+ " (label, streetNumber, street, zipCode, city, country, state, location, place_id, url, formatted_address) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			ps = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getLabel());
			ps.setString(2, obj.getStreetNumber());
			ps.setString(3, obj.getStreet());
			ps.setInt(4, obj.getZipCode());
			ps.setString(5, obj.getCity());
			ps.setString(6, obj.getCountry());
			ps.setString(7, obj.getState());
			ps.setString(8, obj.getLocation().toString());
			ps.setString(9, obj.getPlace_id());
			ps.setString(10, obj.getUrl());
			ps.setString(11, obj.getFormatted_address());

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
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error " + e.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return obj.getId();
	}

	@Override
	public boolean delete(Address obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "DELETE FROM " + this.getTableName() + " WHERE id = ?;";

		try {
			ps = this.connect.prepareStatement(query);

			ps.setLong(1, obj.getId());

			res = ps.execute();

			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error " + e.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public boolean update(Address obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "UPDATE " + this.getTableName()
				+ " SET label = ?, streetNumber = ?, street = ?, zipCode = ?, city = ?, country = ?, state = ?, location = ?, place_id = ?, url = ?, formatted_address = ? WHERE id = ?;";

		try {
			ps = this.connect.prepareStatement(query);

			ps.setString(1, obj.getLabel());
			ps.setString(2, obj.getStreetNumber());
			ps.setString(3, obj.getStreet());
			ps.setInt(4, obj.getZipCode());
			ps.setString(5, obj.getCity());
			ps.setString(6, obj.getCountry());
			ps.setString(7, obj.getState());
			ps.setString(8, obj.getLocation().toString());
			ps.setString(9, obj.getPlace_id());
			ps.setString(10, obj.getUrl());
			ps.setString(11, obj.getFormatted_address());
			ps.setLong(12, obj.getId());

			res = ps.executeUpdate() > 0;

			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error " + e.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public Address find(long id) {
		PreparedStatement ps = null;
		Address addr = null;

		String query = "SELECT * FROM " + this.getTableName() + " WHERE id = ?;";

		try {
			ps = this.connect.prepareStatement(query);

			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				addr = new Address(rs.getLong("id"), rs.getString("label"),
						rs.getString("streetNumber"), rs.getString("street"), rs.getInt("zipCode"), rs.getString("city"),
						rs.getString("country"), rs.getString("state"), new Location(rs.getString("location")), rs.getString("place_id"), rs.getString("url"), rs.getString("formatted_address"));
				addr.addAttachments(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentAddressDAO().find(addr));
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error " + e.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return addr;
	}

	@Override
	protected String getTableName() {
		return "Address";
	}

	@Override
	public ArrayList<Address> findAll() {
		PreparedStatement ps = null;
		ArrayList<Address> addresses = new ArrayList<Address>();

		String query = "SELECT * FROM " + this.getTableName() + ";";

		try {
			ps = this.connect.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Address addr = new Address(rs.getLong("id"), rs.getString("label"),
						rs.getString("streetNumber"), rs.getString("street"), rs.getInt("zipCode"), rs.getString("city"),
						rs.getString("country"), rs.getString("state"), new Location(rs.getString("location")), rs.getString("place_id"), rs.getString("url"), rs.getString("formatted_address"));
				addr.addAttachments(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentAddressDAO().find(addr));
				addresses.add(addr);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error " + e.getSQLState(),
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return addresses;
	}
}
