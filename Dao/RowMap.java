package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMap {
	public abstract Object doMap(ResultSet rs) throws SQLException ;

}
