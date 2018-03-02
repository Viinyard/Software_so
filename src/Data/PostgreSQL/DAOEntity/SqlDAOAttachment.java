package Data.PostgreSQL.DAOEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.DAO;
import Entity.Attachment;

public class SqlDAOAttachment extends DAO<Attachment> {

	public SqlDAOAttachment(Connection connect) {
		super(connect);
	}

	@Override
	public long create(Attachment obj) {
		PreparedStatement ps = null;
		
		String query = "INSERT INTO "+this.getTableName()+" (type, data) values (CAST(? AS AttachmentType), ?);";
		
		try {
			ps = this.connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getType());
			ps.setBytes(2, obj.getByteArray());
			
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
	public boolean delete(Attachment obj) {
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
	public boolean update(Attachment obj) {
		PreparedStatement ps = null;
		boolean res = false;
		String query = "UPDATE "+this.getTableName()+" SET type = ?, data = ? WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setString(1, obj.getType());
			ps.setBytes(2, obj.getByteArray());
			
			res = ps.executeUpdate() > 0;
			
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public Attachment find(long id) {
		PreparedStatement ps = null;
		Attachment att = null;
		
		String query = "SELECT * FROM "+this.getTableName()+" WHERE id = ?;";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				att = new Attachment(
						rs.getLong("id"),
						rs.getString("type"),
						rs.getBytes("data"));
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error "+ e.getSQLState(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return att;
	}

	@Override
	protected String getTableName() {
		return "Attachment";
	}

	@Override
	public ArrayList<Attachment> findAll() {
		PreparedStatement ps = null;
		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		
		String query = "SELECT * FROM "+this.getTableName()+";";
		
		try {
			ps = this.connect.prepareStatement(query);
			
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				attachments.add(new Attachment(
						rs.getLong("id"),
						rs.getString("type"),
						rs.getBytes("data")));
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
