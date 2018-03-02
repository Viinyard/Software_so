package Data;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {
	protected Connection connect = null;
	
	public DAO(Connection connect) {
		this.connect = connect;
	}
	
	protected abstract String getTableName();
	
	public abstract long create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(long id);
	
	public abstract ArrayList<T> findAll();
}
