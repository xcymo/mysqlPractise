package Dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSetMetaData;


import CRUD.DButils;

public class UserDaoImple implements UserDao {

	@Override
	public int addUser(User user) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "insert into user (name,password,birthday,gender,money) values (?,?,?,?,?)" ;
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) ;
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
			ps.setString(4, user.getGender());
			ps.setInt(5, user.getMoney());
			ps.executeUpdate() ;
			rs = ps.getGeneratedKeys() ;
			int id = 0;
			if(rs.next())
				id = rs.getInt(1) ;
			return id ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}
	
	
	
	@Override
	public void addBatchUser() {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "insert into user1 (name,password,birthday,gender,money) values (?,?,?,?,?)" ;
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) ;
			for(int i = 0; i < 1000; i++) {
				User user = new User("test" + i, new java.sql.Date(System.currentTimeMillis()), "male", "iamman", 250) ;
				ps.setString(1, user.getName());
				ps.setString(2, user.getPassword());
				ps.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
				ps.setString(4, user.getGender());
				ps.setInt(5, user.getMoney());
				ps.addBatch();
			}
			ps.executeBatch() ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}

	@Override
	public void deleteUser(int id,String name,String password) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "delete from user where id = ? and name = ? and password = ?" ;
			ps = conn.prepareStatement(sql) ;
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.executeUpdate() ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}

	@Override
	public void UpdateUser(User user) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "update user set password = ?,birthday = ?,gender = ? ,money = ? where id = ? or name = ?" ;
			ps = conn.prepareStatement(sql) ;
			ps.setString(1, user.getPassword());
			ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
			ps.setString(3, user.getGender());
			ps.setInt(4, user.getMoney());
			ps.setInt(5, user.getId());
			ps.setString(6, user.getName());
			ps.executeUpdate() ;
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
	}

	@Override
	public User readUser(int UserId) {
		User user = null ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "select id,name,birthday,gender,money from user where id = ?" ;
			ps = conn.prepareStatement(sql) ;
			ps.setInt(1, UserId);
			rs = ps.executeQuery() ;
			while(rs.next()) {
				user = mappingUser(rs);
			}
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
		return user ;
	}
	
	@Override
	public void readUser(String sql, Object[] parameter) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			ps = conn.prepareStatement(sql) ;
			ParameterMetaData pmd = ps.getParameterMetaData() ;
			int count = pmd.getParameterCount() ;
			if((count > 0 && parameter == null) || count != (parameter == null?0 : parameter.length)) {
				throw new DaoException("SQL语句与参数不匹配！") ;
			}
			for(int i = 1; i <= count; i++) {
				ps.setObject(i, parameter[i-1]);
			}
			rs = ps.executeQuery() ;
			ResultSetMetaData rsmd = rs.getMetaData() ;
			int i = rsmd.getColumnCount() ;
			while(rs.next()) {
				for(int j = 1; j <= i; j++) {
					System.out.print(rs.getObject(j) + "\t");
				}
				System.out.println();
			}
			
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
		
	}
	
	@Override
	public List<Map<String, Object>> readUser(String sql) {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		List<Map<String, Object>> datalist = null;
		try {
			conn = DButils.getConnection();
			stmt = conn.createStatement() ;
			rs = stmt.executeQuery(sql) ;
			ResultSetMetaData rsmd = rs.getMetaData() ;
			int count = rsmd.getColumnCount(); 
			datalist = new ArrayList<>() ;
			while(rs.next()) {
				Map<String, Object> data = new LinkedHashMap<String, Object>() ;
				for(int i = 1; i <= count; i++) {
					data.put(rsmd.getColumnName(i), rs.getObject(i)) ;
				}
				datalist.add(data) ;
			}
			
		}catch(SQLException e)  {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, stmt, conn);
		}
		return datalist ;
	}
	
	@Override
	public List<Object> readObject(String sql,Class clazz) {
		Connection conn = null ;
		Statement stmt = null ;
		ResultSet rs = null ;
		List<Object> list = null ; 
		try {
			conn = DButils.getConnection();
			stmt = conn.createStatement() ;
			rs = stmt.executeQuery(sql) ;
			ResultSetMetaData rsmd = rs.getMetaData() ;
			int count = rsmd.getColumnCount() ;
			String[] colName = new String[count] ;
			Method[] methods = clazz.getMethods() ;
			for (int i = 1; i <= count; i++ ) {
				colName[i-1] = rsmd.getColumnLabel(i) ;
			}
			String methodName;
			list = new ArrayList<>() ;
			while(rs.next()) {
				Object obj = clazz.newInstance() ;
				for(int i = 0; i < count; i++) {
					methodName = "set" + colName[i] ;
					for(Method m : methods) {
						if(m.getName().equals(methodName)) {
							m.invoke(obj, rs.getObject(colName[i])) ;
						}
					}
				}
				list.add(obj) ;
			}
		}catch(Exception e)  {
			throw new DaoException(e.getMessage(),e) ;
		}
		finally {
			DButils.closeAll(rs, stmt, conn);
		}
		return list;
	}

	@Override
	public User findUser(String name, String password) {
		User user = null ;
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "select id,name,birthday,gender,money from user where name = ? and password = ?" ;
			ps = conn.prepareStatement(sql) ;
			ps.setString(1, name);
			ps.setString(2, password);
			rs = ps.executeQuery() ;
			while(rs.next()) {
				user = mappingUser(rs);
			}
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(),e) ;
		}finally {
			DButils.closeAll(rs, ps, conn);
		}
		return user ;
	}
	
	private User mappingUser(ResultSet rs) throws SQLException {
		User user;
		user = new User() ;
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setBirthday(rs.getDate("birthday"));
		user.setGender(rs.getString("gender"));
		user.setMoney(rs.getInt("money"));
		return user;
	}

}
