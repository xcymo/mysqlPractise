package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CRUD.DButils;

public class UserDaoImple implements UserDao {

	@Override
	public void addUser(User user) {
		Connection conn = null ;
		PreparedStatement ps = null ;
		ResultSet rs = null ;
		try {
			conn = DButils.getConnection();
			String sql = "insert into user (name,password,birthday,gender,money) values (?,?,?,?,?)" ;
			ps = conn.prepareStatement(sql) ;
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
			ps.setString(4, user.getGender());
			ps.setInt(5, user.getMoney());
			ps.executeUpdate() ;
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
