package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.communication.SillyCommunication;
import hu.schonherz.y2014.partyappandroid.util.offlinedatabase.LocalDatabaseUtil;

import java.util.List;

public class Session {
	private static Session instance = null;
	
	User actualUser;
	List<Club> searchViewCLubs;
	boolean isOnline;
	
	CommunicationInterface actualCommunicationInterface;
	LocalDatabaseUtil databaseConnecter;
	
	protected Session() {
	   actualCommunicationInterface = new SillyCommunication();
	   databaseConnecter = new LocalDatabaseUtil(null);
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
		instance.actualUser = null;
		instance.searchViewCLubs = null;
	}
	
	public CommunicationInterface getActualCommunicationInterface() {
		return actualCommunicationInterface;
	}
	
	public LocalDatabaseUtil getDatabaseConnecter() {
		return databaseConnecter;
	}
}
