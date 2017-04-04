package Dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	private static UserDao userdao = null ;
	private static DaoFactory dft= new DaoFactory() ;
	
	private DaoFactory()  {
		try {
			InputStream is = DaoFactory.class.getClassLoader()
					.getResourceAsStream("DaoConfig.properties") ;
			Properties prop = new Properties() ;
			prop.load(is);
			String userdaoclass = prop.getProperty("userDaoClass") ;
			Class clazz = Class.forName(userdaoclass) ;
			userdao = (UserDao)clazz.newInstance() ;
			
		} catch (Throwable e) {
			throw new DaoFactoryException(e.getMessage(), e) ;
		} 
	}
	
	public static DaoFactory getInstance() {
		return dft;
	}
	public UserDao getUserDao() {
		return userdao ;
	}
}
