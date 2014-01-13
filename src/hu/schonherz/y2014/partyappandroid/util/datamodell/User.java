package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class User {
	public int id;
	public String nick_name;
	public String password;
	public String email;
	public int sex;
	public String birthday;
	public int type;
	
	public List<Club> favoriteClubs;
	public List<Club> usersClubs;
	
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
