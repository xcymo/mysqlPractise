package JDBC_try;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import CRUD.DButils;


public class pics {
	public static void main(String[] args0) {
//		picUpload();
		picDownload();
	}
	
	public static void picUpload() {
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection() ;
			String sql = "insert into pics(name) values (?)" ;
			stmt = conn.prepareStatement(sql) ;
			File f = new File("D:\\图片\\帅气的我.jpg") ;
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f)) ;
			stmt.setBinaryStream(1, br, (int)f.length());
			stmt.executeUpdate() ;
			br.close();
		}catch(Exception e) {
			
		}finally {
			DButils.closeAll(rs, stmt, conn);
		}
	}
	public static void picDownload() {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection() ;
			String sql = "select name from pics where id = 1" ;
			ps = conn.prepareStatement(sql) ;
			rs = ps.executeQuery() ;
			
			while (rs.next()) {
				InputStream is = rs.getBinaryStream("name");
				FileOutputStream fos = new FileOutputStream(new File("pics.jpg"));
				byte[] arr = new byte[1024];
				int i;
				while ((i = is.read(arr)) != -1) {
					fos.write(arr, 0, i);
				} 
			}
		}catch(Exception e) {
			
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}
}
