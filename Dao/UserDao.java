package Dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
	public void addUser(User user) ;
	
	public void addBatchUser(List list) ;
	
	public void deleteUser(int id,String name,String password) ;
	
	public void UpdateUser(String gender,int id) ;
	
	public User readUser(int UserId) ;
	
	public void readUser(String sql,Object[] parameter) ;

	public List<Object> readObject(String sql,Class clazz) ;
	
	public List<Map<String,Object>> readUser(String sql) ;
	
	public User findUser(String name, String password) ;
	
}
