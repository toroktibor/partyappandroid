package hu.schonherz.y2014.partyappandroid.util.offlinedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "partyapp.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_USER = "user";
	private static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+" ("+
	        "id integer  NOT NULL,"+
	        "nick_name varchar2(255)  NOT NULL,"+
	        "password varchar2(50)  NOT NULL,"+
	        "email varchar2(255)  NOT NULL,"+
	        "sex smallint  NOT NULL,"+
	        "birthday date  NOT NULL,"+
	        "type smallint NOT NULL,"+
	        "last_update datetime NOT NULL,"+
	        "PRIMARY KEY (id)"+
	") ;";
	
	private static final String TABLE_OWNER = "owner";
	private static final String CREATE_TABLE_OWNER = "CREATE TABLE "+TABLE_OWNER+" ("+
			"club_id integer NOT NULL,"+
			"user_id integer NOT NULL,"+
			"approved smallint NOT NULL,"+
			"PRIMARY KEY (club_id,user_id)"+
	");";
	
	private static final String TABLE_CLUB = "club";
	private static final String CREATE_TABLE_CLUB = "CREATE TABLE "+TABLE_CLUB+" ("+
			"id integer  NOT NULL,"+
			"name varchar2(255)  NOT NULL,"+
			"type  varchar2(50)  NOT NULL,"+
			"description varchar2(255)  NULL,"+
			"address varchar2(50)  NOT NULL,"+
			"phonenumber varchar2(50) NULL,"+
			"email varchar2(255) NULL,"+
			"highlight_expire date  NULL,"+
			"PRIMARY KEY (id)"+
	") ;";
	
	private static final String TABLE_FAVORITE = "favorite";
	private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE "+TABLE_FAVORITE+" ("+
			"id integer  NOT NULL,"+
			"user_id integer  NOT NULL,"+
			"club_id integer  NOT NULL,"+
			"FOREIGN KEY(user_id) REFERENCES user(id),"+
			"FOREIGN KEY(club_id) REFERENCES club(id),"+
			"PRIMARY KEY (id)"+
	") ;";
	
	private static final String TABLE_MENUITEM = "menu_item";
	private static final String CREATE_TABLE_MENUITEM = "CREATE TABLE "+TABLE_MENUITEM+" ("+
			"id integer  NOT NULL,"+
			"club_id integer  NOT NULL,"+
			"name varchar2(255)  NOT NULL,"+
			"price integer  NOT NULL,"+
			"discount integer  NOT NULL,"+
			"menu_category varchar2(255)  NOT NULL,"+
			"menu_sort smallint  NOT NULL,"+
			"currency varchar2(30)  NOT NULL,"+
			"unit varchar2(30)  NOT NULL,"+
			"FOREIGN KEY(club_id) REFERENCES club(id),"+
			"PRIMARY KEY (id)"+
	") ;";
	
	private static final String TABLE_EVENT = "event";
	private static final String CREATE_TABLE_EVENT = "CREATE TABLE "+TABLE_EVENT+" ("+
			"id integer  NOT NULL,"+
			"club_id integer  NOT NULL,"+
			"name varchar2(255)  NOT NULL,"+
			"description varchar2(255)  NOT NULL,"+
			"start_date datetime  NOT NULL,"+
			"music_type varchar2(50)  NOT NULL,"+
			"row_image blob NULL,"+
			"FOREIGN KEY(club_id) REFERENCES club(id),"+
			"PRIMARY KEY (id)"+
	") ;";
	
	private static final String TABLE_SERVICE = "service";
	private static final String CREATE_TABLE_SERVICE = "CREATE TABLE "+TABLE_SERVICE+" ("+
			"id integer  NOT NULL,"+
    		"club_id integer  NOT NULL,"+
    		"service_name varchar2(20)  NOT NULL,"+
    		"FOREIGN KEY(club_id) REFERENCES club(id),"+
    		"PRIMARY KEY (id)"+
    ") ;";
	
	public LocalDatabaseOpenHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_OWNER);
		db.execSQL(CREATE_TABLE_CLUB);
		db.execSQL(CREATE_TABLE_FAVORITE);
		db.execSQL(CREATE_TABLE_MENUITEM);
		db.execSQL(CREATE_TABLE_EVENT);
		db.execSQL(CREATE_TABLE_SERVICE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_OWNER);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CLUB);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_FAVORITE);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_MENUITEM);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_EVENT);
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SERVICE);
		
		onCreate(db);
		
	}

//    private static final String DATABASE_NAME = "partyapp";
//    private static final int DATABASE_VERSION = 1;
//    private static String DB_PATH = "/data/data/hu.schonherz.y2014.partyappandroid.util.offlinedatabase;/databases/";
//    private SQLiteDatabase myDataBase;
//    private final Context myContext;
//
//    public LocalDatabaseOpenHelper(Context context) {
//	super(context, DATABASE_NAME, null, DATABASE_VERSION);
//	this.myContext = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase arg0) {
//	// TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
//	// TODO Auto-generated method stub
//
//    }
//
//    /**
//     * Creates a empty database on the system and rewrites it with your own
//     * database.
//     * */
//    public void createDataBase() throws IOException {
//
//	boolean dbExist = checkDataBase();
//
//	if (dbExist) {
//	    // do nothing - database already exist
//	} else {
//
//	    // By calling this method and empty database will be created into
//	    // the default system path
//	    // of your application so we are gonna be able to overwrite that
//	    // database with our database.
//	    this.getReadableDatabase();
//
//	    try {
//
//		copyDataBase();
//
//	    } catch (IOException e) {
//
//		throw new Error("Error copying database");
//
//	    }
//	}
//
//    }
//
//    /**
//     * Check if the database already exist to avoid re-copying the file each
//     * time you open the application.
//     * 
//     * @return true if it exists, false if it doesn't
//     */
//    private boolean checkDataBase() {
//
//	SQLiteDatabase checkDB = null;
//
//	try {
//	    String myPath = DB_PATH + DATABASE_NAME;
//	    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//	} catch (SQLiteException e) {
//
//	    // database does't exist yet.
//
//	}
//
//	if (checkDB != null) {
//
//	    checkDB.close();
//
//	}
//
//	return checkDB != null ? true : false;
//    }
//
//    /**
//     * Copies your database from your local assets-folder to the just created
//     * empty database in the system folder, from where it can be accessed and
//     * handled. This is done by transfering bytestream.
//     * */
//    private void copyDataBase() throws IOException {
//
//	// Open your local db as the input stream
//	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
//
//	// Path to the just created empty db
//	String outFileName = DB_PATH + DATABASE_NAME;
//
//	// Open the empty db as the output stream
//	OutputStream myOutput = new FileOutputStream(outFileName);
//
//	// transfer bytes from the inputfile to the outputfile
//	byte[] buffer = new byte[1024];
//	int length;
//	while ((length = myInput.read(buffer)) > 0) {
//	    myOutput.write(buffer, 0, length);
//	}
//
//	// Close the streams
//	myOutput.flush();
//	myOutput.close();
//	myInput.close();
//
//    }
//
//    public void openDataBase() throws SQLException {
//
//	// Open the database
//	String myPath = DB_PATH + DATABASE_NAME;
//	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//
//    }
//
//    @Override
//    public synchronized void close() {
//
//	if (myDataBase != null)
//	    myDataBase.close();
//
//	super.close();
//
//    }

}
