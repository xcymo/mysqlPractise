package CRUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;



public class DButils {
	private static DataSource mydataSource = null ;
	
	static{
		try {
			Properties prop = new Properties();
//			InputStream is = DButils.class.getClassLoader()
//			.getResourceAsStream("dbcpconfig.properties") ;
			InputStream is = new FileInputStream(new File("dbcpconfig.properties")) ;
			prop.load(is);
			Class.forName(prop.getProperty("driverClassName")) ;
			mydataSource = BasicDataSourceFactory.createDataSource(prop) ;
		} catch (Exception e) {
			e.printStackTrace();
//			throw new ExceptionInInitializerError();
		}
		
	}
	
	
	public static Connection getConnection() throws SQLException {
		return mydataSource.getConnection() ;
	}
	
	public static void closeAll(ResultSet rs, 
								Statement stmt, 
								Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
