package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.services.GPSLocation;
import hu.schonherz.y2014.partyappandroid.services.GPSLocation.LocalBinder;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends ActionBarActivity {

    boolean mBounded;
    GPSLocation mGpsLocation;
    // String cityname = "Budapest";
    // create new async task for fetching location and execute it
    public static LoginActivity instance = null;
    LocationWorker locationTask = new LocationWorker();

    class LocationWorker extends AsyncTask<Boolean, Integer, Boolean> {

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onPostExecute(Boolean result) {
	    /*
	     * Here you can call myLocationHelper.getLat() and
	     * myLocationHelper.getLong() to get the location data.
	     */
	    if (mGpsLocation.gotLocation() == true) {
		Session.getInstance().citynameFromGPS = mGpsLocation.getCityName();
		Log.e("async", "lekérdezett város : " + mGpsLocation.getCityName());
	    }
	}

	@Override
	protected Boolean doInBackground(Boolean... params) {

	    // while the location helper has not got a lock
	    while (isCancelled() == false) {
		if (mGpsLocation.gotLocation() == true) {
		    break;
		}
	    }
	    // once done return true
	    return true;
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	instance=this;
	
	new SimpleActionBar(this, "Bejelentkezés").setLayout();

	setContentView(R.layout.activity_login);

	Session.getInstance().makeLocalDatabaseConnection(getApplicationContext());
	Session.getInstance().getDatabaseConnecter().open();

	// gyors teszt
	// Session.getInstance().getDatabaseConnecter().testMethod();
	// gyors teszt vege

	if (Session.getInstance().getActualUser() != null) {
	    loginSynchronize(Session.getInstance().getActualUser(), getApplicationContext());
	    Intent newIntent = new Intent(this, ClubsActivity.class);
	    startActivity(newIntent);
	    finish();
	}

    }

    @Override
    protected void onStart() {
	super.onStart();
	Intent mIntent = new Intent(this, GPSLocation.class);
	bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    };

    ServiceConnection mConnection = new ServiceConnection() {

	public void onServiceDisconnected(ComponentName name) {
	    mBounded = false;
	    mGpsLocation = null;
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
	    mBounded = true;
	    LocalBinder mLocalBinder = (LocalBinder) service;
	    mGpsLocation = mLocalBinder.getServerInstance();

	    if (InternetConnection.isOnline(getApplicationContext())
		    && locationTask.getStatus() == AsyncTask.Status.PENDING) {
	    	Log.i("async", "lefuttatja");
		locationTask.execute(new Boolean[] { true });
	    }

	}
    };

    @Override
    protected void onStop() {
	super.onStop();
	if (mBounded) {
	    unbindService(mConnection);
	    mBounded = false;
	}

    };

    public void onClickHandler(View v) {
	switch (v.getId()) {
	case R.id.login_button_login:
	    final String usernameFromEditText = ((EditText) findViewById(R.id.login_edittext_name)).getEditableText()
		    .toString();

	    Log.e("LOGIN SCREEN - CATCHED USERNAME", usernameFromEditText);
	    final String passwordFromEditText = ((EditText) findViewById(R.id.login_edittext_password)).getEditableText()
		    .toString();
	    Log.e("LOGIN SCREEN - CATCHED PASSWORD: ", passwordFromEditText);
	    
	    
	    Runnable r = new Runnable() {
	        
	        @Override
	        public void run() {
	            
	            final User actualUser = Session.getInstance().getActualCommunicationInterface()
			    .authenticationUser(usernameFromEditText, passwordFromEditText);
	            
	            if (actualUser != null) {
	        	// Ha a nĂ©v Ă©s jelszĂł pĂˇros helyes
			Log.e("LOGIN SCREEN", "CORRECT USERNAME-PASSWORD PAIR, CLUBSACTIVITY STARTING");
			mGpsLocation.onDestroy();
			locationTask.cancel(true);

			loginSynchronize(actualUser, getApplicationContext());
	            }
	            
	            
	            Activity a = LoginActivity.this;
	            a.runOnUiThread(new Runnable() {
		        
		        @Override
		        public void run() {
		            if (actualUser == null) {
		        	Session.getInstance().dismissProgressDialog();
				Log.e("LOGIN SCREEN", "WRONG NAME-PASSWORD PAIR, TOAST WILL BE SHOWED");				
				new ErrorToast(LoginActivity.this, "Sikertelen bejelentkezés! Hibásfelhasználónév vagy jelszó! Próbáld újra!").show();
				Log.e("LOGIN SCREEN", "TOAST SHOWN SUCCESSFULLY");
			    } else {
				Intent newIntent = new Intent(LoginActivity.this, ClubsActivity.class);
				
				startActivity(newIntent);
				overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
				Log.e("LOGIN SCREEN", "CLUBSACTIVITY STARTED");
				finish();
			    }	
		            Session.getInstance().dismissProgressDialog();
		        }
		    });
	            		    
	        }
	    };
	    
	    Session.getInstance().progressDialog=ProgressDialog.show(this, "Kérlek várj", "Bejelentkezés folyamatban...", true, false);
	    new Thread(r).start();
	    
	    break;

	case R.id.login_button_register:
	    Log.e("REGISTER", "REGISTER BUTTON PRESSED, REGISTERACTIVITY STARTING");
	    startActivity(new Intent(this, RegisterActivity.class));
	    Log.e("REGISTER", "REGISTERACTIVITY STARTED");
	}
    }

    public static void loginSynchronize(User actualUser, Context context) {
	Session.setActualUser(actualUser);
	String cityname = Session.getInstance().citynameFromGPS;
	Log.e("LOGIN SYNCHRONIZE", "LOGIN IN PROGRESS, NAME OF ACTUAL CITY: " + cityname);
	actualUser.favoriteClubs = Session.getInstance().getActualCommunicationInterface()
		.getFavoriteClubsFromUserId(actualUser.getId());
	Log.e("LOGIN SYNCHRONIZE", "FAVOURITE CLUBS CATCHED");
	Session.setSearchViewClubs(Session.getInstance().getActualCommunicationInterface()
		.getClubsFromCityName(cityname));
	
	//ideírd
	Session.getInstance().setPositions(context);
	Log.e("LOGIN SYNCHRONIZE", "CLUBS IN THE ACTUAL CITY (" + cityname + ") CATCHED");
	Session.getActualUser().usersClubs = Session.getInstance().getActualCommunicationInterface()
		.getOwnedClubsFromUserId(actualUser.getId());
	Log.e("LOGIN SYNCHRONIZE", "USER'S OWN CLUBS CATCHED");
    }

    User loginOnline(Context context, User actualUser) {
	Log.e("LOGIN ONLINE", "STARTED");
	User result = null;
	Log.e("LOGIN ONLINE", "FINISHED");
	return result;
    }

    User loginOffline(Context context, User actualUser) {
	Log.e("LOGIN OFFLINE", "STARTED");
	User result = null;
	Log.e("LOGIN OFFLINE", "FINISHED");
	return result;
    }

}
