package hu.schonherz.y2014.partyappandroid.util.datamodell;

import java.util.List;

public class Session {
	private static Session instance = null;
	
	User actualUser;
	List<Club> searchViewCLubs;
	
	protected Session() {
	   // Exists only to defeat instantiation.
	}
	public static Session getInstance() {
	   if(instance == null) {
	      instance = new Session();
	   }
	   return instance;
	}
	
	public static User getActualUser() {
		return instance.actualUser;
	}
	
	public static void setActualUser(User actualUser) {
		instance.actualUser = actualUser;
	}
	
	public static List<Club> getSearchViewCLubs() {
		return instance.searchViewCLubs;
	}
	
	public static void setSearchViewCLubs(List<Club> searchViewCLubs) {
		instance.searchViewCLubs = searchViewCLubs;
	}
	
	public static void closeSession() {
		instance = null;
	}
}
