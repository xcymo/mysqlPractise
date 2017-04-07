package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper;

import CRUD.DButils;

public abstract class AbstractDao {
	
	/*
	 * 直接用Update()的功能实现Create和Delete
	 */
	public int Delete(String sql,Object[] args) {
		return Update(sql,args) ;
	}
	public int Create(String sql, Object[] args) {
		return Update(sql, args);
	}
	
	public int Update(String sql,Object[] args) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			ps = conn.prepareStatement(sql) ;
			if(args != null) {
				for(int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
			}
			return ps.executeUpdate() ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}
	
	public Object Read(String sql,int id) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		Object obj = null ;
		try {
			conn = DButils.getConnection();
			ps = conn.prepareStatement(sql) ;
			ps.setInt(1, id);
			rs = ps.executeQuery() ;
			while(rs.next()) {
				obj = rowMap(rs) ;
			}
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
		return obj;
	}

	public void CreateBatch(String sql,List list) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			ps = conn.prepareStatement(sql) ;
			for(Object obj : list) {
				
				Object[] elements = getElement(obj) ;
				for(int i = 0; i < elements.length; i++) {
					ps.setObject(i+1, elements[i]);
				}
				ps.addBatch();
			}
			ps.executeBatch() ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}
	
	protected abstract Object[] getElement(Object obj) ;
	protected abstract Object rowMap(ResultSet rs) throws SQLException ;
}
