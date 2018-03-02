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
import Entity.Attachment;
import Entity.Car;

public class SqlDAOAttachmentCar extends DAOJoin <Attachment, Car> {

	public SqlDAOAttachmentCar(Connection connect) {
		super(connect);
	}

	@Override
	protected String getTableName() {
		return "AttachmentCar";
	}

	@Override
	public boolean create(Attachment obj, Car owner) {
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentDAO().create(obj);
		PreparedStatement ps = null;
		boolean res = false;
		
		String query = "INSERT INTO "+this.getTableName()+" (idAttachment, idCar) values (?, ?);";
		
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
	public ArrayList<Attachment> find(Car owner) {
		PreparedStatement ps = null;
		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE idCar = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, owner.getId());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				attachments.add(AbstractDAOFactory.getFactory(AbstractDAOFactory.SQL).getAttachmentDAO().find(rs.getLong("id")));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return attachments;
	}
}
