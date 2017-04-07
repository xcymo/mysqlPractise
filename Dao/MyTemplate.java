package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;


import CRUD.DButils;

public class MyTemplate {
	public Object find(String sql,Object[] args,RowMap rowMap) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		Object obj = null;
		try{
			
			conn = DButils.getConnection() ;
			ps = conn.prepareStatement(sql) ;
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery() ;
			if (rs.next()) {				//这个位置一定要记得写rs.next()！！找bug找了好久。。。。。。
				obj = rowMap.doMap(rs);
			}
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
		return obj ;
	}
}
