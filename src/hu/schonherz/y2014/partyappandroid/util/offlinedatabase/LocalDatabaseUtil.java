package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;

import android.content.Context;
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

}
