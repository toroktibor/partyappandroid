package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class User {
	int id;
	String nick_name;
	String password;
	String email;
	int sex;
	String birthday;
	int type;
	
	List<Club> favoriteClubs;
	List<Club> usersClubs;
	
	public User(int id, String nick_name, String password, String email, int sex, String birthday, int type) {
		this.id = id;
		this.nick_name = nick_name;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.type = type;
	}
}
