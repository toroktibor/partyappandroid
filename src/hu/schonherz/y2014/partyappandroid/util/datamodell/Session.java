package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.util.communication.Communication;
import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.communication.SillyCommunication;
import hu.schonherz.y2014.partyappandroid.util.offlinedatabase.LocalDatabaseUtil;

import java.util.List;

import android.content.Context;
import android.util.Log;

public class Session {
    private static Session instance = null;

    User actualUser;
    List<Club> searchViewClubs;
    boolean isOnline;

    CommunicationInterface actualCommunicationInterface;
    LocalDatabaseUtil databaseConnecter;

    protected Session() {
    	//actualCommunicationInterface = new Communication(); //meg nem all teljesen keszen
    	actualCommunicationInterface = new SillyCommunication();
    }
    
    public void makeLocalDatabaseConnection(Context context){
    	databaseConnecter = new LocalDatabaseUtil(context);
    }
    
    public static Session getInstance() {
	if (instance == null) {
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

    public static List<Club> getSearchViewClubs() {
	return instance.searchViewClubs;
    }

    public static void setSearchViewClubs(List<Club> searchViewClubs) {
	instance.searchViewClubs = searchViewClubs;
	for (int i = 0; i < searchViewClubs.size(); ++i) {
	    Log.d("LISTAELEMEK", searchViewClubs.get(i).toString());
	}
    }

    public static void closeSession() {
	instance.actualUser = null;
	instance.searchViewClubs = null;
    }

    public CommunicationInterface getActualCommunicationInterface() {
	return actualCommunicationInterface;
    }

    public LocalDatabaseUtil getDatabaseConnecter() {
	return databaseConnecter;
    }
}
