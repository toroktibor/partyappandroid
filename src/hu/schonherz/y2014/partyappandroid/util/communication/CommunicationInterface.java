package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.List;

public interface CommunicationInterface {
	public User authenticationUser(String nick_name, String password);
	public List<Club> getFavoriteClubsFromUserId(int user_id);
	public List<Club> getClubsFromCityName(String cityname);
	
	//Elküld egy új hely kérelmet a szervernek, ha a owner_user_id -1 akkor nincs neki tulaj jelöltje
	public void sendANewClubRequest(String newClubName, String newClubAddress, String newClubType, int owner_user_id);
}
