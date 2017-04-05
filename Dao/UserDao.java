package Dao;

public interface UserDao {
	public int addUser(User user) ;
	
	public void addBatchUser() ;
	
	public void deleteUser(int id,String name,String password) ;
	
	public void UpdateUser(User user) ;
	
	public User readUser(int UserId) ;
	
	public void readUser(String sql,Object[] parameter) ;
	
	public User findUser(String name, String password) ;
	
}
