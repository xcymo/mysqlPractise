package CRUD;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MydataSource implements DataSource {
	private static String driver ;
	private static String url ;
	private static String usersname ;
	private static String password  ;
	private static int initCount = 5;
	private static int maxCount = 8 ;
	private static int currentCount = 0 ;
	
	private static LinkedList<Connection> connectionPool = new LinkedList<>() ;
	
	public MydataSource() {
		ResourceBundle rb = ResourceBundle.getBundle("info") ;
		driver = rb.getString("driver") ;
		url = rb.getString("url") ;
		usersname = rb.getString("usersname") ;
		password = rb.getString("password") ;
		
		try {
			Class.forName(driver) ;
			for(int i = 0; i < initCount; i ++){
				createConnection();
			}
		}catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection createConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url, usersname, password) ;
		connectionPool.addFirst(conn);
		currentCount++;
		return conn;
	}
	
	public Connection getConnection() throws SQLException {
		synchronized (connectionPool) {
			if (connectionPool.size() > 0) {
				return connectionPool.removeLast();
			}
			if (currentCount < maxCount) {
				createConnection();
				return getConnection();
			}
			throw new SQLException("Á¬½ÓÒÑÂú");
		}
	}
	
	public void free(Connection conn) {
		connectionPool.addFirst(conn);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}
}
