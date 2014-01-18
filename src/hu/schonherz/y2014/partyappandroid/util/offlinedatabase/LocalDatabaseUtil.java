package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocalDatabaseUtil {

    private SQLiteDatabase database;
    private LocalDatabaseOpenHelper dbHelper;

    public LocalDatabaseUtil(Context context) {
	dbHelper = new LocalDatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
	database = dbHelper.getWritableDatabase();
    }

    public void close() {
	dbHelper.close();
    }

    public User loginWithNickName(String nickName, String password) {
	String selectQuery = String.format("SELECT * FROM user WHERE nick_name = %s AND password = %s", nickName,
		password);
	Cursor cursor = database.rawQuery(selectQuery, null);

	if (cursor.getCount() == 0) {
	    return null;
	}

	User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
		cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5),
		Integer.parseInt(cursor.getString(6)));

	return user;
    }

    public void insertUser(String nickName, String password, String email, int sex, String birthday, int type) {
    
	ContentValues values = new ContentValues();

	//values.put("id", id);
	values.put("nick_name", nickName);
	values.put("password", password);
	values.put("email", email);
	values.put("sex", sex);
	values.put("birthday", birthday);
	values.put("type", type);

	database.insert("user", null, values);
    }

    public void updateUser(int id, String nickName, String password, String email, int sex, String birthday, int type) {

	ContentValues values = new ContentValues();

	values.put("nick_name", nickName);
	values.put("password", password);
	values.put("email", email);
	values.put("sex", sex);
	values.put("birthday", birthday);
	values.put("type", type);

	database.update("user", values, "id = ?", new String[] { String.valueOf(id) });
    }

    public void updateUserPassword(int id, String newPassword) {
	ContentValues values = new ContentValues();

	values.put("password", newPassword);

	database.update("user", values, "id = ?", new String[] { String.valueOf(id) });
    }

    public ArrayList<Club> userFavouriteClubbs(int id) {
	ArrayList<Club> list = new ArrayList<Club>();

	String selectQuery = String.format("SELECT club.* FROM user INNER JOIN favorite "
		+ "ON user.id = favorite.user_id INNER JOIN club ON "
		+ "favorite.club_id = club.id WHERE favorite.user_id = %s ORDER BY "
		+ "club.highlight_expire DESC, club.name", Integer.toString(id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	if (cursor.moveToFirst()) {
	    do {
		Club club = new Club(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
			cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
			cursor.getString(7), cursor.getString(8), Integer.parseInt(cursor.getString(9)));
		list.add(club);
	    } while (cursor.moveToNext());
	}

	return list;
    }

    public ArrayList<Club> userOwnClubs(int id) {
	ArrayList<Club> list = new ArrayList<Club>();

	String selectQuery = String.format("SELECT club.* FROM user INNER JOIN"
		+ " owner ON user.id = owner.user_id INNER JOIN club ON "
		+ "owner.club_id = club.id WHERE owner.user_id = %s ORDER "
		+ "BY club.highlight_expire DESC, club.name", Integer.toString(id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	if (cursor.moveToFirst()) {
	    do {
		Club club = new Club(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
			cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
			cursor.getString(7), cursor.getString(8), Integer.parseInt(cursor.getString(9)));
		list.add(club);
	    } while (cursor.moveToNext());
	}

	return list;
    }

    public Club searchFavoriteClub(int id, String adress, String type, String service) {

	String selectQuery = String.format(
		"SELECT DISTINCT club.* FROM user INNER JOIN "
			+ "favorite ON user.id = favorite.user_id INNER JOIN club ON "
			+ "favorite.club_id = club.id INNER JOIN service ON " + "club.id = service.club_id WHERE "
			+ "favorite.user_id = %s AND " + "club.address LIKE '%%s%' AND "
			+ "club.type = %s AND service.service_name IN (%s) ORDER "
			+ "BY club.highlight_expire DESC, club.name", Integer.toString(id), adress, type, service);

	Cursor cursor = database.rawQuery(selectQuery, null);

	Club club = new Club(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
		cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
		cursor.getString(7), cursor.getString(8), Integer.parseInt(cursor.getString(9)));

	return club;
    }

    public Club searchOwnClub(int id, String adress, String type, String service) {

	String selectQuery = String.format("SELECT DISTINCT club.* FROM user INNER JOIN owner "
		+ "ON user.id = owner.user_id INNER JOIN club " + "ON owner.club_id = club.id INNER JOIN service "
		+ "ON club.id = service.club_id	WHERE owner.user_id = %s "
		+ "AND club.address LIKE '%%s%' AND club.type = %s AND s"
		+ "ervice.service_name IN (%s) ORDER BY club.highlight_expire " + "DESC, club.name",
		Integer.toString(id), adress, type, service);

	Cursor cursor = database.rawQuery(selectQuery, null);

	Club club = new Club(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
		cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
		cursor.getString(7), cursor.getString(8), Integer.parseInt(cursor.getString(9)));

	return club;
    }

    public void insertClub(String name, String type, String description, String adress, String phonenumber,
	    String email, String date, String highlite_expire, int approved) {

	ContentValues values = new ContentValues();

	// values.put("id", id);
	values.put("name", name);
	values.put("type", type);
	values.put("description", description);
	values.put("adress", adress);
	values.put("phonenumber", phonenumber);
	values.put("email", email);
	values.put("highlight_expire", highlite_expire);
	values.put("approved", approved);

	database.insert("club", null, values);

    }

    public void updateClub(int id, String name, String type, String description, String adress, String phonenumber,
	    String email, String date, String highlite_expire, int approved) {

	ContentValues values = new ContentValues();

	values.put("name", name);
	values.put("type", type);
	values.put("description", description);
	values.put("adress", adress);
	values.put("phonenumber", phonenumber);
	values.put("email", email);
	values.put("highlight_expire", highlite_expire);
	values.put("approved", approved);

	database.update("club", values, "id = ?", new String[] { String.valueOf(id) });
    }

    public Club clubInformation(int id) {
	String selectQuery = String.format("SELECT * FROM club WHERE id = %s", Integer.toString(id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	Club club = new Club(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
		cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
		cursor.getString(7), cursor.getString(8), Integer.parseInt(cursor.getString(9)));

	return club;
    }

    public void deleteClub(int id) {

	database.delete("club", "id = ?", new String[] { String.valueOf(id) });

    }

    // események listája
    public Event eventInformationFromEventId(int event_id) {
	Event out;

	String selectQuery = String.format("SELECT * FROM event WHERE id = %d;", Integer.toString(event_id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	out = new Event(event_id, cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6),
		Integer.parseInt(cursor.getString(7)));

	return out;
    }

    // új esemény
    public void insertNewEvent(int id, int club_id, String name, String description, String start_date,
	    String music_type) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("club_id", club_id);
	values.put("name", name);
	values.put("description", description);
	values.put("start_date", start_date);
	values.put("music_type", music_type);

	database.insert("event", null, values);
    }

    // esemény frissítése
    public void updateEvent(int id, int club_id, String name, String description, String start_date, String music_type) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("club_id", club_id);
	values.put("name", name);
	values.put("description", description);
	values.put("start_date", start_date);
	values.put("music_type", music_type);

	database.update("event", values, "id = ?", new String[] { Integer.toString(id) });

    }

    // esemény törlése
    public void deleteEvent(int id) {

	database.delete("event", "id = ?", new String[] { String.valueOf(id) });

    }

    // kedvenc hely beszúrása
    public void insertNewFavoriteClub(int id, int user_id, int club_id) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("user_id", user_id);
	values.put("club_id", club_id);

	database.insert("favorite", null, values);
    }

    // kedvenc hely frissítése
    public void updateFavoriteClub(int id, int user_id, int club_id) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("user_id", user_id);
	values.put("club_id", club_id);

	database.update("favorite", values, "id = ?", new String[] { Integer.toString(id) });

    }

    // kedvenc hely törlése
    public void deleteFavoriteClub(int id) {

	database.delete("favorite", "id = ?", new String[] { String.valueOf(id) });

    }

    // saját hely beszúrása
    public void insertNewOwnClub(int user_id, int club_id, int approved) {
	ContentValues values = new ContentValues();

	values.put("user_id", user_id);
	values.put("club_id", club_id);
	values.put("approved", approved);

	database.insert("owner", null, values);
    }

    // saját hely frissítése(jóváhagyás)
    public void updateOwnClub(int user_id, int club_id, int approved) {
	ContentValues values = new ContentValues();

	values.put("user_id", user_id);
	values.put("club_id", club_id);
	values.put("approved", approved);

	database.update("owner", values, "club_id = ? AND user_id = ?", new String[] { Integer.toString(club_id),
		Integer.toString(user_id) });
    }

    // saját hely törlése
    public void deleteOwnClub(int user_id, int club_id, int approved) {

	database.delete("owner", "club_id = ? AND user_id = ?",
		new String[] { Integer.toString(club_id), Integer.toString(user_id) });

    }

    // kedvencek eseményeinek listája
    public ArrayList<Event> favoritesEventInformation(int user_id) {
	ArrayList<Event> out = new ArrayList<Event>();

	String selectQuery = String.format("SELECT event.*" + "FROM user"
		+ "INNER JOIN favorite ON user.id = favorite.user_id" + "INNER JOIN club ON favorite.club_id = club.id"
		+ "INNER JOIN event ON club.id = event.club_id" + "WHERE favorite.user_id = %d;",
		Integer.toString(user_id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	if (cursor.moveToFirst()) {
	    do {
		Event actual_event = new Event(Integer.parseInt(cursor.getString(0)), cursor.getString(2),
			cursor.getString(3), cursor.getString(4), cursor.getString(5), 1);
		out.add(actual_event);
	    } while (cursor.moveToNext());
	}

	return out;
    }

    // árlista megjelenítése
    public ArrayList<MenuItem> menuItemsPrice(int club_id) {
	ArrayList<MenuItem> out = new ArrayList<MenuItem>();

	String selectQuery = String
		.format("SELECT menu_item.* FROM club INNER JOIN menu_item ON club.id = menu_item.club_id  WHERE menu_item.club_id = %d;",
			Integer.toString(club_id));

	Cursor cursor = database.rawQuery(selectQuery, null);

	if (cursor.moveToFirst()) {
	    do {
		MenuItem actual_menuItem = new MenuItem(Integer.parseInt(cursor.getString(0)), cursor.getString(2),
			Integer.parseInt(cursor.getString(3)), cursor.getString(7), cursor.getString(8),
			Integer.parseInt(cursor.getString(4)), cursor.getString(5), Integer.parseInt(cursor
				.getString(6)));
		out.add(actual_menuItem);
	    } while (cursor.moveToNext());
	}

	return out;
    }

    // árlista elem beszúrása
    public void insertNewMenuItemsPrice(int id, int club_id, String name, int price, int discount,
	    String menu_category, int menu_sort, String currency, String unit) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("club_id", club_id);
	values.put("name", name);
	values.put("price", price);
	values.put("discount", discount);
	values.put("menu_category", menu_category);
	values.put("menu_sort", menu_sort);
	values.put("currency", currency);
	values.put("unit", unit);

	database.insert("menu_item", null, values);
    }

    // árlista elem frissítése
    public void updateMenuItemsPrice(int id, int club_id, String name, int price, int discount, String menu_category,
	    int menu_sort, String currency, String unit) {
	ContentValues values = new ContentValues();

	values.put("id", id);
	values.put("club_id", club_id);
	values.put("name", name);
	values.put("price", price);
	values.put("discount", discount);
	values.put("menu_category", menu_category);
	values.put("menu_sort", menu_sort);
	values.put("currency", currency);
	values.put("unit", unit);

	database.update("menu_item", values, "id = ?", new String[] { Integer.toString(id) });
    }

    // árlista elem törlése
    public void deleteMenuItemsPrice(int id) {
	ContentValues values = new ContentValues();

	database.delete("menu_item", "id = ?", new String[] { Integer.toString(id) });
    }
}
