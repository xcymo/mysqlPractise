package JDBC_try;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import CRUD.DButils;
import Dao.DaoFactory;
import Dao.User;
import Dao.UserDao;
import Dao.UserDaoImple;

public class demo1 {

	public static void main(String[] args) throws Exception {
		UserDao userdao = DaoFactory.getInstance().getUserDao();
		User u = new User() ;
		u.setName("锐儿");
//		u.setId(8);
		u.setBirthday(new Date(94, 0, 3));
		u.setGender("female");
		u.setPassword("iamre");
		
		
//		udi.addUser(u);
//		User u = udi.findUser("兮儿", "iamxe") ;
//		udi.UpdateUser(u);
//		User u = udi.readUser(7) ;
		userdao.UpdateUser(u);
	}
}
