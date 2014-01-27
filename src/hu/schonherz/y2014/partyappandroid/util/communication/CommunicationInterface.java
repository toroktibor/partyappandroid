package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

public interface CommunicationInterface {
    public User authenticationUser(String nickname, String password);

    public List<Club> getFavoriteClubsFromUserId(int user_id);

    public List<Club> getClubsFromCityName(String cityname);

    public List<Club> getOwnedClubsFromUserId(int user_id);
    
    public Club getEverythingFromClub(int club_id);
    
    public String httpPost(String url,HashMap<String, String> post) throws ClientProtocolException, IOException;

    /**
     * Adatbázissal kommunikáló metódus a klubbok kereséshez, melynek
     * paraméterei a kereséshez feltételei és logikai kapcsolattal. A keresési
     * eredmények az offset és limit paraméterekkel limitálhatóak.
     * 
     * @param name
     *            a klub neve tartalmazza ezt a karakterláncot
     * @param cityname
     *            a klub városa megegyezzen ezzel a karakterlánccal
     * @param type
     *            a klub típusa megegyezzen ezzel a karakterlánccal
     * @param offset
     *            a keresési eredmények szűkítéséhez használható eltolási érték
     * @param limit
     *            a keresési eredmények szűkítéséhez használható számossági
     *            korlátozás, 0 esetén nincs korlát
     * @return a keresés feltételeinek megfelelő klubbok listájával tér vissza
     * 
     * @throws Exception
     *             sikertelen adatbázis kommunikációt jelző kivétel
     */
    public List<Club> searchClubs(String name, String cityname, String type, int offset, int limit) throws Exception;

    /**
     * Megadott felhasználóhoz tartozó jelszó módosítása a távoli adatbázisban.
     * 
     * @param nickname
     *            kiválasztott felhasználó adatbázis ID-je
     * @param password
     *            az új jelszó *
     * 
     * @throws a
     *             módosítás sikertelenségét jelző kivétel
     */
    public void modifyPassword(int id, String password) throws Exception;

    /**
     * Megadott felhasználóhoz tartozó adatok módosítása a távoli adatbázisban.
     * 
     * @param id
     *            kiválasztott felhasználó ID-je
     * @param email
     *            az új email cím
     * @param birthday
     *            az új születési dátum
     * @param sex
     *            az új neme ( férfi=0, nő=1 )
     * 
     * @throws a
     *             módosítás sikertelenségét jelző kivétel
     */
    public void modifyUserData(int id, String email, String birthday, int sex) throws Exception;
    
    public User registerANewUser(String nick_name, String password, String email, int sex, String birthday);
    
    public void setServices(int club_id, List<String> services);
    
    public void setOwnerForClub(int user_id, int club_id);
    
    public void setFavoriteClubForUser(int user_id, int club_id);
    
    public void deleteFavoriteClubForUser(int club_id, int user_id);

	public void sendANewClubRequest(String newClubName, String newClubAddress,
			String newClubType, int owner_user_id, List<String> services);
	
	public List<Club> getNotApprovedClubs();
	
	public void approveClub(int club_id);
	
	public void declineNewClub(int club_id);
	
	public void declineOwnerRequest(int club_id, int user_id);
	
	public void acceptOwnerRequest(int club_id, int user_id);
	
	public List<OwnerRequest> getNotApprovedOwnerRequest();
	
	public void uploadAnImage(int club_id, String rowImage);
	
	public String DownLoadAnImage(int id);
	
	public ArrayList<Integer> selectClubsImagesIds(int club_id);
	
	public int addANewMenuItem(int clubId, MenuItem menuItem);

	public List<MenuItem> getMenuItemsForClub(int club_id);
	
	public void updateAMenuItem(MenuItem menuItem);
	
	public void removeEMenuItem(int menuid);

	public List<Event> getEventsOfClub(int id);
}
