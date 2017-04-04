package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.junit.Test;

import JDBC_try.Emp;


public class DButils {
	
	private static String driver ;
	private static String url ;
	private static String usersname ;
	private static String password  ;
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("info") ;
		driver = rb.getString("driver") ;
		url = rb.getString("url") ;
		usersname = rb.getString("usersname") ;
		password = rb.getString("password") ;
		try {
			Class.forName(driver) ;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//创建连接
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, usersname, password) ;
	}
	//关闭流
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
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
	
	@Test
	public static void testSelect(int empno) {
		Connection conn = null ;
		PreparedStatement ps = null ; 
		ResultSet rs = null ;
		
		try {
			conn = getConnection() ;
			String sql = "select * from emp where empno = ?" ;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empno);
			rs = ps.executeQuery() ;
			
			ArrayList<Emp> list = new ArrayList<>() ;
			while(rs.next()) {
				Emp e = new Emp() ;
				e.setComm(rs.getInt("comm"));
				e.setDeptno(rs.getInt("deptno"));
				e.setEmpno(rs.getInt("empno"));
				e.setEname(rs.getString("ename"));
				e.setHiredate((Date)rs.getObject("hiredate"));
				e.setJob(rs.getString("job"));
				e.setMgr(rs.getInt("mgr"));
				e.setSal(rs.getInt("sal"));
				list.add(e) ;
			}
			closeAll(rs,ps,conn) ;
			for (Emp emp : list) {
				System.out.println(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public static void testInsert() {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		
		try {
			conn = getConnection() ;
			stmt = conn.createStatement() ;
			int i = stmt.executeUpdate("insert into emp (empno,ename,job,mgr,"
					+ "hiredate,sal,comm,deptno) values (7979,'JASON','MANAGER',"
					+ "7839,'1989-8-8',1515,0,20)") ;
			closeAll(rs,stmt,conn) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public static void testDelete() {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		
		try {
			conn = getConnection() ;
			stmt = conn.createStatement() ;
			int i = stmt.executeUpdate("delete from emp where empno = 7979") ;
			closeAll(rs,stmt,conn) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public static void testUpdate() {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		
		try {
			conn = getConnection() ;
			stmt = conn.createStatement() ;
			int i = stmt.executeUpdate("update emp set sal = 5555 where empno = 7979") ;
			closeAll(rs,stmt,conn) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
