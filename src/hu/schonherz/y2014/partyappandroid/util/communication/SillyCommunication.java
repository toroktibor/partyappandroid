package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.ArrayList;
import java.util.List;

public class SillyCommunication implements CommunicationInterface {

    public SillyCommunication() {
	// TODO Auto-generated constructor stub
    }

    public User authenticationUser(String nick_name, String password) {
	if (nick_name == null || password == null)
	    return null;
	else if (nick_name.equals("a") && password.equals("a")) {
	    return new User(1, "a", "a", "teszta@emailcim.com", 0, "1990.03.21", 0);
	} else if (nick_name.equals("b") && password.equals("b")) {
	    return new User(2, "b", "b", "tesztb@emailcim.com", 0, "1990.03.21", 0);
	} else if (nick_name.equals("c") && password.equals("c")) {
	    return new User(3, "c", "c", "tesztc@emailcim.com", 0, "1990.03.21", 1);
	}
	return null;
    }

    public List<Club> getFavoriteClubsFromUserId(int user_id) {
	ArrayList<Club> favoriteClubs = new ArrayList<Club>();
	favoriteClubs.add(new Club(1, "Dance", "Budapest, Váci utca 49"));
	return favoriteClubs;
    }

    public List<Club> getClubsFromCityName(String cityname) {

	ArrayList<Club> cityClubs = new ArrayList<Club>();

	if (cityname.equals("Debrecen")) {
	    cityClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
	    cityClubs.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
	}
	if (cityname.equals("Budapest")) {
	    cityClubs.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
	    cityClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
	}
	
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
	    ret.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
	    ret.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
	} else if (cityname.equals("Budapest")) {
	    ret.add(new Club(2, "Táncolj", "Budapest, Kishegyesi utca 48"));
	    ret.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
	} else {
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
	    ownedClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
	    ownedClubs.add(new Club(3, "Dance", "Budapest, Váci utca 49"));
	}

	return ownedClubs;
    }
}
