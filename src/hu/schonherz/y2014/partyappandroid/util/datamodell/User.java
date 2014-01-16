package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class User {
	private int id;
	private String nickname;
	private String password;
	private String email;
	/**
	 * 0 = Férfi
	 * 1 = Nő
	 */
	private int sex;
	private String birthday;
	/**
	 * A
	 * 0 = vendég
	 * 1 = tulaj
	 * 2 = admin
	 */
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

	public 
	
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
	
	public void modifyUserData(String email, String birthday, int sex) throws Exception{
	    Session.getInstance().getActualCommunicationInterface().modifyUserData(id, email, birthday, sex);
	    this.email=email;
	    this.birthday=birthday;
	    this.sex=sex;
	}
	
	public void modifyPassword(String newPassword) throws Exception{
	    Session.getInstance().getActualCommunicationInterface().modifyPassword(id, newPassword);
	    this.password=newPassword;
	}
	
	
	
}
