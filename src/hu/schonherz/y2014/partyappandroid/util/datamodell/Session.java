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
	
	public User getActualUser() {
		return actualUser;
	}
	
	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}
	
	public List<Club> getSearchViewCLubs() {
		return searchViewCLubs;
	}
	
	public void setSearchViewCLubs(List<Club> searchViewCLubs) {
		this.searchViewCLubs = searchViewCLubs;
	}
}
