package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "partyapp";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/hu.schonherz.y2014.partyappandroid.util.offlinedatabase;/databases/";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public LocalDatabaseOpenHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub

    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {

	boolean dbExist = checkDataBase();

	if (dbExist) {
	    // do nothing - database already exist
	} else {

	    // By calling this method and empty database will be created into
	    // the default system path
	    // of your application so we are gonna be able to overwrite that
	    // database with our database.
	    this.getReadableDatabase();

	    try {

		copyDataBase();

	    } catch (IOException e) {

		throw new Error("Error copying database");

	    }
	}

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     * 
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

	SQLiteDatabase checkDB = null;

	try {
	    String myPath = DB_PATH + DATABASE_NAME;
	    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	} catch (SQLiteException e) {

	    // database does't exist yet.

	}

	if (checkDB != null) {

	    checkDB.close();

	}

	return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

	// Open your local db as the input stream
	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

	// Path to the just created empty db
	String outFileName = DB_PATH + DATABASE_NAME;

	// Open the empty db as the output stream
	OutputStream myOutput = new FileOutputStream(outFileName);

	// transfer bytes from the inputfile to the outputfile
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer)) > 0) {
	    myOutput.write(buffer, 0, length);
	}

	// Close the streams
	myOutput.flush();
	myOutput.close();
	myInput.close();

    }

    public void openDataBase() throws SQLException {

	// Open the database
	String myPath = DB_PATH + DATABASE_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

	if (myDataBase != null)
	    myDataBase.close();

	super.close();

    }

}
