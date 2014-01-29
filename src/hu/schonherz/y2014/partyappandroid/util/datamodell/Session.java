package hu.schonherz.y2014.partyappandroid.util.datamodell;

import hu.schonherz.y2014.partyappandroid.util.communication.Communication;
import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.offlinedatabase.LocalDatabaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

public class Session {
    private static Session instance = null;

    public String citynameFromGPS = "Debrecen";
    public ProgressDialog progressDialog = null;
    
    User actualUser;
    List<Club> searchViewClubs;
    boolean isOnline;

    CommunicationInterface actualCommunicationInterface;
    LocalDatabaseUtil databaseConnecter;

    protected Session() {
	actualCommunicationInterface = new Communication();
    }

    public void dismissProgressDialog(){
	if(progressDialog!=null){
	    progressDialog.dismiss();
	    progressDialog=null;
	}
    }
    
    public void setPositions(Context context) {
    	List<Address> addressList = new ArrayList<Address>();
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		Club actualClub;
		for (int i = 0; i < searchViewClubs.size(); ++i) {
			actualClub = searchViewClubs.get(i);
			try {
				//Log.e("SESSION", actualClub.address);
				addressList = geocoder.getFromLocationName(actualClub.address, 1);
				actualClub.position = new LatLng(addressList.get(0).getLatitude(), 
												addressList.get(0).getLongitude());
				//Log.e("SESSION - CLUBS LATLNG=", ((Double) (addressList.get(0).getLatitude())).toString() + "/" +
				//		((Double) (addressList.get(0).getLongitude())).toString());
				//Log.e("SESSION ADDRESSLIST: ",geocoder.getFromLocationName(actualClub.address, 1).get(0).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    	return;
    }
    
    public void makeLocalDatabaseConnection(Context context) {
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
