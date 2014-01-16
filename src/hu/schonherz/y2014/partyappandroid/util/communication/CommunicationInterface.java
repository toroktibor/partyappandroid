package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.List;

public interface CommunicationInterface {
	public User authenticationUser(String nickname, String password);
	public List<Club> getFavoriteClubsFromUserId(int user_id);
	public List<Club> getClubsFromCityName(String cityname);
	public List<Club> getOwnedClubsFromUserId(int user_id);
	
	//Elküld egy új hely kérelmet a szervernek, ha a owner_user_id -1 akkor nincs neki tulaj jelöltje
	public void sendANewClubRequest(String newClubName, String newClubAddress, String newClubType, int owner_user_id);
	
	/**
	 * Adatbázissal kommunikáló metódus a klubbok kereséshez, melynek paraméterei a kereséshez feltételei és logikai kapcsolattal.
	 * A keresési eredmények az offset és limit paraméterekkel limitálhatóak.
	 * 
	 * @param name a klub neve tartalmazza ezt a karakterláncot
	 * @param cityname a klub városa megegyezzen ezzel a karakterlánccal
	 * @param type a klub típusa megegyezzen ezzel a karakterlánccal
	 * @param offset a keresési eredmények szűkítéséhez használható eltolási érték
	 * @param limit a keresési eredmények szűkítéséhez használható számossági korlátozás, 0 esetén nincs korlát
	 * @return a keresés feltételeinek megfelelő klubbok listájával tér vissza
	 * 
	 * @throws Exception sikertelen adatbázis kommunikációt jelző kivétel 
	 */
	public List<Club> searchClubs(String name,String cityname,String type, int offset, int limit) throws Exception;
	
	/**
	 * Megadott felhasználóhoz tartozó jelszó módosítása a távoli adatbázisban.
	 * 
	 * @param nickname kiválasztott felhasználó adatbázis ID-je
	 * @param password az új jelszó	 *
	 * 
	 * @throws a módosítás sikertelenségét jelző kivétel 
	 */
	public void modifyPassword(int id, String password) throws Exception;
	
	/**
	 * Megadott felhasználóhoz tartozó adatok módosítása a távoli adatbázisban.
	 * 
	 * @param id kiválasztott felhasználó ID-je
	 * @param email az új email cím
	 * @param birthday az új születési dátum
	 * @param sex az új neme ( férfi=0, nő=1 )
	 * 
	 * @throws a módosítás sikertelenségét jelző kivétel
	 */
	public void modifyUserData(int id, String email, String birthday, int sex) throws Exception;
}
