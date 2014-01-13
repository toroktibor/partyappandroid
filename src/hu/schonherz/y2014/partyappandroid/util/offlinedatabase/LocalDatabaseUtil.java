package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;
import java.util.ArrayList;

import hu.schonherz.y2014.partyappandroid.util.datamodell.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
	  
	  
	  public User loginWintNickName(String nickName, String password){
		  String selectQuery =String.format("SELECT * FROM user WHERE nick_name = %s AND password = %s",nickName,password);
		  Cursor cursor=database.rawQuery(selectQuery, null);
		  
		  User user;
		  
		  if (cursor.getCount()==0) {
			return null;
		}
		  
		  user=new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
				  cursor.getString(3),Integer.parseInt(cursor.getString(4)),cursor.getString(5),
				  Integer.parseInt(cursor.getString(6)));
		  
		  return user;
	  }
	 
	  
	  
	  public void insertUser(int id,String nickName,String password,String email,int sex,String birthday,
			  int type){
		  
		  ContentValues values=new ContentValues();
		  
		  values.put("id", id);
		  values.put("nick_name", nickName);
		  values.put("password", password);
		  values.put("email", email);
		  values.put("sex", sex);
		  values.put("birthday", birthday);
		  values.put("type", type);
		  
		  database.insert("user", null, values);
	  }
	  
	  
	  
	  public void updateUser(int id,String nickName,String password,String email,int sex,String birthday,
			  int type){
		  
		  ContentValues values=new ContentValues();

		  values.put("nick_name", nickName);
		  values.put("password", password);
		  values.put("email", email);
		  values.put("sex", sex);
		  values.put("birthday", birthday);
		  values.put("type", type);
		  
		  database.update("user", values, "id = ?",new String[] { String.valueOf(id)});  
	  }
	  
	  
	  public void updateUserPassword(int id,String newPassword){
		  ContentValues values=new ContentValues();
		  
		  values.put("password", newPassword);
		  
		  database.update("user", values, "id = ?",new String[] { String.valueOf(id)});  
	  }
	  
	  
	  public ArrayList<Club> userFavouriteClubbs(int id){
		  ArrayList<Club> list=new ArrayList<Club>();
		  
		  String selectQuery =String.format("SELECT club.* FROM user INNER JOIN favorite "
		  		+ "ON user.id = favorite.user_id INNER JOIN club ON "
		  		+ "favorite.club_id = club.id WHERE favorite.user_id = %s ORDER BY "
		  		+ "club.highlight_expire DESC, club.name",Integer.toString(id));
		  
		  Cursor cursor=database.rawQuery(selectQuery, null);
		  
		  if (cursor.moveToFirst()) {
		        do {
		            Club club = new Club(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
		            		cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),
		            		cursor.getString(6),cursor.getString(7),cursor.getString(8),Integer.parseInt(cursor.getString(9)));
		            list.add(club);
		        } while (cursor.moveToNext());
		    }
		  
		  
		  return list;
	  }
	  
	  
	  
	  public ArrayList<Club> OwnClub(int id){
ArrayList<Club> list=new ArrayList<Club>();
		  
		  String selectQuery =String.format("SELECT club.* FROM user INNER JOIN"
		  		+ " owner ON user.id = owner.user_id INNER JOIN club ON "
		  		+ "owner.club_id = club.id WHERE owner.user_id = %s ORDER "
		  		+ "BY club.highlight_expire DESC, club.name;",Integer.toString(id));
		  
		  Cursor cursor=database.rawQuery(selectQuery, null);
		  
		  if (cursor.moveToFirst()) {
		        do {
		            Club club = new Club(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
		            		cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),
		            		cursor.getString(6),cursor.getString(7),cursor.getString(8),Integer.parseInt(cursor.getString(9)));
		            list.add(club);
		        } while (cursor.moveToNext());
		    }
		  
		  
		  return list;
	  }
	  
	  
	  public ArrayList<Club> searchFavoriteClubb(int id,String adress,String type,String service){
		  ArrayList<Club> list=new ArrayList<Club>();
		  		  
		  		  String selectQuery =String.format("SELECT DISTINCT club.* FROM user INNER JOIN "
		  		  		+ "favorite ON user.id = favorite.user_id INNER JOIN club ON "
		  		  		+ "favorite.club_id = club.id INNER JOIN service ON "
		  		  		+ "club.id = service.club_id WHERE "
		  		  		+ "favorite.user_id = %s AND "
		  		  		+ "club.address LIKE '%%s%' AND "
		  		  		+ "club.type = %s AND service.service_name IN (%s) ORDER "
		  		  		+ "BY club.highlight_expire DESC, club.name;",Integer.toString(id),adress,type,service);
		  		  
		  		  Cursor cursor=database.rawQuery(selectQuery, null);
		  		  
		  		  if (cursor.moveToFirst()) {
		  		        do {
		  		            Club club = new Club(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
		  		            		cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),
		  		            		cursor.getString(6),cursor.getString(7),cursor.getString(8),Integer.parseInt(cursor.getString(9)));
		  		            list.add(club);
		  		        } while (cursor.moveToNext());
		  		    }
		  		  
		  		  
		  		  return list;
		  	  }

}
