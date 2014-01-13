package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "contacts.db";
	private static final int DATABASE_VERSION = 1;

	public LocalDatabaseOpenHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
