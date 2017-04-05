package Dao;

public interface UserDao {
	public int addUser(User user) ;
	
	public void deleteUser(int id,String name,String password) ;
	
	public void UpdateUser(User user) ;
	
	public User readUser(int UserId) ;
	
	public User findUser(String name, String password) ;
	
}
