package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.ArrayList;
import java.util.List;

public class SillyCommunication implements CommunicationInterface {
	public User authenticationUser(String nick_name, String password){
		if(nick_name.equals("Gábor") && password.equals("bcsapat")){
			return new User(1,"Gábor","bcsapat","g@g.com",1,"1990.01.01",0);
		}
		return null;
	}
	public List<Club> getFavoriteClubsFromUserId(int user_id){
		return null;
	}
	public List<Club> getClubsFromCityName(String cityname){
		
		ArrayList<Club> favoriteClubs = new ArrayList<Club>();
		
		favoriteClubs.add(new Club(0, "Le'Programoz-Lak", "Debrecen, Kishegyesi utca 48"));
		favoriteClubs.add(new Club(1, "Le'Programoz-Lak-Koppintás", "Debrecen, Kishegyesi utca 49"));
		
		return favoriteClubs;
	}
}
