package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class User {
	private int id;
	private String nickname;
	private String password;
	private String email;
	private int sex;
	private String birthday;
	private int type;
	

	public List<Club> favoriteClubs;
	public List<Club> usersClubs;
	
	public User(int id, String nickname, String password, String email, int sex, String birthday, int type) {
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.type = type;
	}

	public int getId() {
	    return id;
	}

	public String getNickname() {
	    return nickname;
	}

	public String getPassword() {
	    return password;
	}

	public String getEmail() {
	    return email;
	}

	public int getSex() {
	    return sex;
	}

	public String getBirthday() {
	    return birthday;
	}

	public int getType() {
	    return type;
	}
	
	
	
}
