package JDBC_try;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Date;

import CRUD.DButils;
import Dao.DaoFactory;
import Dao.User;
import Dao.UserDao;

public class demo2 {
	public static void main (String[] arg0) throws SQLException {
		test();
		
	}
	public static void test() throws SQLException {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		Savepoint sp = null ;
		try {
			conn = DButils.getConnection() ;
			conn.setAutoCommit(false);
			stmt = conn.createStatement() ;
			
			UserDao ud = DaoFactory.getInstance().getUserDao();
			User u = new User("阿瘪", new Date(94,1,3), "famale", "iamab", 1000) ;
			ud.addUser(u);
			sp = conn.setSavepoint() ;
			
			String sql = "select money from user where id = 8" ;
			rs = stmt.executeQuery(sql) ;
			if(rs.next() && rs.getInt("money") > 2000)
				throw new RuntimeException("这个人的钱太多了！") ;
			
			sql = "update user set money = money - 200 where id = 4" ;
			stmt.executeUpdate(sql) ;

			sql = "update user set money = money + 200 where id = 8" ;
			stmt.executeUpdate(sql) ;
			
			conn.commit();
		}catch(SQLException e) {
			if(conn != null){
				conn.rollback(sp);
				conn.commit();
			}
			throw e;
		}finally {
			
		}
	}
}
