package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class SillyCommunication implements CommunicationInterface {

    public SillyCommunication() {
	// TODO Auto-generated constructor stub
    }

    public User authenticationUser(String nick_name, String password) {
		if (nick_name == null || password == null) {
			Log.e("AUTHENTICATIONUSER","SOMETHING IS NULL");
		    return null;
		}
		else if (nick_name.equals("a") && password.equals("a")) {
			Log.e("AUTHENTICATIONUSER","GUEST USER 1 LOADED");
		    return new User(1, "a", "a", "teszta@emailcim.com", 0, "1990.03.21", 0);
		    }
		else if (nick_name.equals("b") && password.equals("b")) {
			Log.e("AUTHENTICATIONUSER","GUEST USER 2 LOADED");
		    return new User(2, "b", "b", "tesztb@emailcim.com", 0, "1990.03.21", 0);
		    }
		else if (nick_name.equals("c") && password.equals("c")) {
			Log.e("AUTHENTICATIONUSER","ADMIN USER LOADED");
		    return new User(3, "c", "c", "tesztc@emailcim.com", 0, "1990.03.21", 1);
		}
		Log.e("AUTHENTICATIONUSER","NO USER (NULL) LOADED");
	return null;
    }

    public List<Club> getFavoriteClubsFromUserId(int user_id) {
		ArrayList<Club> favoriteClubs = new ArrayList<Club>();
		favoriteClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
		Log.e("GETFAVORITECLUBS","FAVOURITE PLACES CATCHED");
		Log.e("GETFAVORITECLUBS","NOW THE FAVORITE PLACES ARE GIVEN BACK");
	return favoriteClubs;
    }

    public List<Club> getClubsFromCityName(String cityname) {
		ArrayList<Club> cityClubs = new ArrayList<Club>();
		if (cityname.equals("Debrecen")) {
		    cityClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
		    cityClubs.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
		    Log.e("GETCLUBSFROMCITYNAME","PLACES OF " + cityname + " CITY CATCHED");
		}
		else if (cityname.equals("Budapest")) {
		    cityClubs.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
		    cityClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
		    Log.e("GETCLUBSFROMCITYNAME","PLACES OF " + cityname + " CITY CATCHED");
		}
		else {
			cityClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
		    cityClubs.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
		    cityClubs.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
		    cityClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
		    Log.e("GETCLUBSFROMCITYNAME","PLACES OF UNKNOWN CITY CATCHED");
		}
		Log.e("GETCLUBSFROMCITYNAME","NOW THE RESULT PLACES ARE GIVEN BACK");
	return cityClubs;
    }

    @Override
    public void sendANewClubRequest(String newClubName, String newClubAddress, String newClubType, int owner_user_id) {
	// TODO Auto-generated method stub

    }

    @Override
    public List<Club> searchClubs(String name, String cityname, String type, int offset, int limit) throws Exception {
	List<Club> ret = new ArrayList<Club>();

	/*
	 * ret.add(new Club(0, "Le'Programoz-Lak",
	 * "Debrecen, Kishegyesi utca 48")); ret.add(new Club(1,
	 * "Le'Programoz-Lak-KoppintĂˇs", "Debrecen, Kishegyesi utca 49"));
	 * ret.add(new Club(2, "Harmadik", "Debrecen, Kishegyesi utca 50"));
	 * ret.add(new Club(3, "Negyedik", "Debrecen, Kishegyesi utca 51"));
	 * 
	 * 
	 * // A talĂˇlatok szĹ±kĂ­tĂ©se egyelĹ‘re csak nĂ©v szerint mĹ±kĂ¶dik //
	 * ezt nem en irtam for( Club club: ret){ if( !club.name.contains(name)
	 * ){ ret.remove(club); } }
	 */

		if (cityname.equals("Debrecen")) {
			Log.e("SEARCHCLUBS","PLACES OF " + cityname + " CITY CATCHED");
		    ret.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
		    ret.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
		} 
		else if (cityname.equals("Budapest")) {
			Log.e("SEARCHCLUBS","PLACES OF " + cityname + " CITY CATCHED");
		    ret.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
		    ret.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
		} 
		else {
			Log.e("SEARCHCLUBS","PLACES OF UNKNOWN CITY CATCHED");
		    ret.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
		    ret.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
		    ret.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
		    ret.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
		}

	return ret;

    }

    @Override
    public void modifyPassword(int id, String password) throws Exception {
	// TODO: módosítás az adatbázisban
    }

    @Override
    public void modifyUserData(int id, String email, String birthday, int sex) throws Exception {
	// TODO: módosítás az adatbázisban
    }

    @Override
    public List<Club> getOwnedClubsFromUserId(int user_id) {

	ArrayList<Club> ownedClubs = new ArrayList<Club>();

	if (user_id == 2) {
		Log.e("GETOWNEDCLUBS","USER'S OWN PLACES LOADED");
	    ownedClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
	    ownedClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
	}
	else {
		Log.e("GETOWNEDCLUBS", "THIS USER DOESN'T HAVE OWN PLACES");
	}
	Log.e("GETOWNEDCLUBS","NOW THE RESULT PLACES ARE GIVEN BACK");
	return ownedClubs;
    }

	@Override
	public Club getEverythingFromClub(int club_id) {
		
		Club fullClub;
		
		if(club_id == 0){
			fullClub = new Club(0, "Le'Programoz-Lak", "Kocsma", "Nagyon kellemes hely ahol akciós a mac és ahol a kockák el vannak vetve.", "Debrecen, Kishegyesi utca 48", "06306665551", "le@programoz.com", "2014.10.10", "2014.10.10", 1);
			fullClub.menuItems = new ArrayList<MenuItem>();
			fullClub.ratings = new ArrayList<Rating>();
			fullClub.events = new ArrayList<Event>();
			fullClub.services = new ArrayList<String>();
			
			fullClub.events.add(new Event(0, "Programozunk!!", "Hardcore programozás lesz éjjel nappal!!", "2014.01.20", "Metal", 1));
			fullClub.events.add(new Event(1, "Bajba leszünk", "Én nem tudom mit fogunk bemutatni :S", "2014.01.24", "Mulatós", 1));
			fullClub.events.add(new Event(2, "Alvás", "Szarni nem lesz energiád, garantált!", "2014.02.01", "Vegyes", 1));
			
			fullClub.menuItems.add(new MenuItem(0, "Kőbányai", 180, "Ft", "üveg", 0, "Ital", 0));
			fullClub.menuItems.add(new MenuItem(0, "MAC", 1000, "Ft", "darab", 0, "Rezsó", 0));
			fullClub.menuItems.add(new MenuItem(0, "Panellakás", 100000, "Ft", "darab", 20, "Rezsó", 0));
			
		} else if(club_id == 1){
			fullClub = new Club(1, "Le'Programoz-Lak-Koppintás", "Kocsma", "Nagyon kellemes hely ahol akciós a mac és ahol a kockák el vannak vetve.", "Debrecen, Kishegyesi utca 49", "06306665551", "le@programoz.com", "2014.10.10", "2014.10.10", 1);
			fullClub.menuItems = new ArrayList<MenuItem>();
			fullClub.ratings = new ArrayList<Rating>();
			fullClub.events = new ArrayList<Event>();
			fullClub.services = new ArrayList<String>();
		}else if(club_id == 2){
			fullClub = new Club(2, "Táncolj", "Sport-csarnok", "Nagyon kellemes hely ahol akciós a mac és ahol a kockák el vannak vetve.", "Budapest, Kishegyesi utca 48", "06306665551", "le@programoz.com", "2014.10.10", "2014.10.10", 1);
			fullClub.menuItems = new ArrayList<MenuItem>();
			fullClub.ratings = new ArrayList<Rating>();
			fullClub.events = new ArrayList<Event>();
			fullClub.services = new ArrayList<String>();
		}else if(club_id == 3){
			fullClub = new Club(3, "Dance", "Disco", "Nagyon kellemes hely ahol akciós a mac és ahol a kockák el vannak vetve.", "Budapest, Váci utca 49", "06306665551", "le@programoz.com", "2014.10.10", "2014.10.10", 1);
			fullClub.menuItems = new ArrayList<MenuItem>();
			fullClub.ratings = new ArrayList<Rating>();
			fullClub.events = new ArrayList<Event>();
			fullClub.services = new ArrayList<String>();
		} else {
			fullClub = null;
			Log.i("itt",(new Integer(club_id)).toString());
		}
		
		return fullClub;
	}

	@Override
	public User registerANewUser(String nick_name, String password,
			String email, int sex, String birthday) {
		// TODO Auto-generated method stub
		return null;
	}
}
