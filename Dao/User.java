package Dao;

import java.util.Date;

public class User {
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthday=" + birthday + ", gender=" + gender + ", money="
				+ money + "]";
	}
	private int id ;
	private String name ;
	private Date birthday ;
	private String gender ;
	private String password ;
	private int money ;
	
	public User(int id, String name, Date birthday, String gender, String password, int money) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.password = password;
		this.money = money;
	}
	public User( String name, Date birthday, String gender, String password, int money) {
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.password = password;
		this.money = money;
	}
	public User() {
		
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
