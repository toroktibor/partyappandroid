package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.ArrayList;
import java.util.List;

public class SillyCommunication implements CommunicationInterface {
	
	public SillyCommunication() {
		// TODO Auto-generated constructor stub
	}
	
	public User authenticationUser(String nick_name, String password){
		if(nick_name == null || password == null )
			return null;
		else if(nick_name.equals("a") && password.equals("a")){
			return new User(1,"a","a","g@g.com",1,"1990.01.01",0);
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

	@Override
	public void sendANewClubRequest(String newClubName, String newClubAddress,
			String newClubType, int owner_user_id) {
		// TODO Auto-generated method stub
		
	}
}
