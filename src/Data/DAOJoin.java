package Data;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAOJoin<T, J> {
	protected Connection connect = null;
	
	public DAOJoin(Connection connect) {
		this.connect = connect;
	}
	
	protected abstract String getTableName();
	
	public abstract boolean create(T obj, J owner);
	
	public abstract ArrayList<T> find(J owner);
}
