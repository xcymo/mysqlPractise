package JDBC_try;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import javax.naming.directory.InvalidSearchControlsException;

import org.junit.Test;

import CRUD.DButils;


public class Clob {

	public static void main(String[] args) throws Throwable {
		insertClob();
		readClob();
	}
	public static void readClob() throws Throwable {
		Connection conn = null ;
		Statement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection() ;
			String sql = "select txt from clob" ;
			ps = conn.prepareStatement(sql) ;
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				Reader reader = rs.getClob(1).getCharacterStream();
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("text.txt"),true)) ;
				char[] arr = new char[50] ;
				int len = 0;
				while ((len = reader.read(arr)) != -1) {
					writer.write(arr, 0, len);
				}
				reader.close();
				writer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DButils.closeAll(rs, ps, conn);
	}
	}
	
	
	public static void insertClob() throws Throwable {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection() ;
			String sql = "insert into clob (txt) values (?)" ;
			ps = conn.prepareStatement(sql) ;
			File file = new File("text.txt");
			BufferedReader br = new BufferedReader(new FileReader(file)) ;
			ps.setCharacterStream(1, br, (int)file.length());
			
			ps.executeUpdate();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}
//	
//	@Test
//	public void create() throws Exception{
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			// 2.建立连接
//			conn = DButils.getConnection();
//			// conn = JdbcUtilsSing.getInstance().getConnection();
//			// 3.创建语句
//			String sql = "insert into clob(txt) values (?) ";
//			ps = conn.prepareStatement(sql);
//			File file = new File("text.txt");
//			Reader reader = new BufferedReader(new FileReader(file));
//
//			ps.setCharacterStream(1, reader, (int) file.length());
//			// ps.setString(1, x);
//			// 4.执行语句
//			int i = ps.executeUpdate();
//
//			reader.close();
//
//			System.out.println("i=" + i);
//		} finally {
//			DButils.closeAll(rs, ps, conn);
//		}
//	}
}
