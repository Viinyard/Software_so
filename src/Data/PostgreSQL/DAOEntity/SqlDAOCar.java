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
import Entity.Car;

public class SqlDAOCar extends DAO<Car> {

	public SqlDAOCar(Connection connect) {
		super(connect);
	}

	@Override
	public long create(Car obj) {
		PreparedStatement ps = null;
		String query = "INSERT INTO "+this.getTableName()+" (model, fiscalHorsepower, registrationDate, idOwner) values (?, ?, ?, ?);";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getModel());
			ps.setInt(2, obj.getFiscalHorsepower());
			ps.setDate(3, new java.sql.Date(obj.getRegistrationDate()));
			ps.setLong(4, obj.getIdEmployee());
			
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
	public boolean delete(Car obj) {
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
	public boolean update(Car obj) {
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "UPDATE "+this.getTableName()+" SET model = ?, fiscalHorspower = ?, registrationDate = ?, idEmployee = ? WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getModel());
			ps.setInt(2, obj.getFiscalHorsepower());
			ps.setDate(3, new java.sql.Date(obj.getRegistrationDate()));
			ps.setLong(4, obj.getIdEmployee());
			ps.setLong(5, obj.getId());
			
			res = ps.executeUpdate() > 0;
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public Car find(long id) {
		PreparedStatement ps = null;
		Car car = null;
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				car = new Car(
						rs.getLong("id"),
						rs.getString("model"),
						rs.getInt("fiscalHorsepower"),
						rs.getDate("registrationDate").getTime(),
						rs.getLong("idEmployee"));
				AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentCarDAO().find(car);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return car;
	}

	@Override
	protected String getTableName() {
		return "Car";
	}

	@Override
	public ArrayList<Car> findAll() {
		PreparedStatement ps = null;
		ArrayList<Car> cars = new ArrayList<Car>();
		
		String query = "SELECT * FROM "+this.getTableName()+";";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Car car = new Car(
						rs.getLong("id"),
						rs.getString("model"),
						rs.getInt("fiscalHorsepower"),
						rs.getDate("registrationDate").getTime(),
						rs.getLong("idEmployee"));
				AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentCarDAO().find(car);
				cars.add(car);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return cars;
	}

}
