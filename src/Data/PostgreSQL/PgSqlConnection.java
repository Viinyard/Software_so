package Data.PostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PgSqlConnection {

	private String url = "jdbc:postgresql://localhost:5432/kilofees";
	private String user = "VinYarD";
	private String password = "AppleTest";
	private static Connection connect;

	private PgSqlConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			PgSqlConnection.connect = DriverManager.getConnection(this.url, this.user, this.password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static Connection getInstance() {
		if (PgSqlConnection.connect == null) {
			new PgSqlConnection();
		}
		return PgSqlConnection.connect;
	}
}
