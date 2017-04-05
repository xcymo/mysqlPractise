package Dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
	public int addUser(User user) ;
	
	public void addBatchUser() ;
	
	public void deleteUser(int id,String name,String password) ;
	
	public void UpdateUser(User user) ;
	
	public User readUser(int UserId) ;
	
	public void readUser(String sql,Object[] parameter) ;
	
	public List<Map<String,Object>> readUser(String sql) ;
	
	public User findUser(String name, String password) ;
	
}
