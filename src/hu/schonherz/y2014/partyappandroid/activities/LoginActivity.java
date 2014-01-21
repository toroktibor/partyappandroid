package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.services.GPSLocation;
import hu.schonherz.y2014.partyappandroid.services.GPSLocation.LocalBinder;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;

public class LoginActivity extends Activity
/*
 * implements GooglePlayServicesClient.ConnectionCallbacks,
 * GooglePlayServicesClient.OnConnectionFailedListener
 */{

	// private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	// LocationClient mLocationClient;

	boolean mBounded;
	GPSLocation mGpsLocation;
	String cityname = "Budapest";
	// create new async task for fetching location and execute it
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
				cityname = mGpsLocation.getCityName();
				Log.e("async",
						"lekérdezett város : " + mGpsLocation.getCityName());
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
			if (isCancelled())
				Log.e("async", "async task is cancelled");
			Log.e("async", "isCancelled: " + isCancelled());
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		Session.getInstance().makeLocalDatabaseConnection(
				getApplicationContext());
		Session.getInstance().getDatabaseConnecter().open();

//		 gyors teszt
//		 Session.getInstance().getDatabaseConnecter().testMethod();
//		 gyors teszt vege

		if (Session.getInstance().getActualUser() != null) {
			loginSynchronize(Session.getInstance().getActualUser());
			Intent newIntent = new Intent(this, ClubsActivity.class);
			startActivity(newIntent);
			finish();
		}

		// mLocationClient = new LocationClient(this, this, this);
		// Location mCurrentLocation;
		// mCurrentLocation = mLocationClient.getLastLocation();
		// System.out.print("ITT VAGYUNK HĂ‰Ă‰Ă‰J!!!!!!!!!!!!!!: ");
		// mCurrentLocation.toString();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent mIntent = new Intent(this, GPSLocation.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	};

	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(LoginActivity.this, "Service is disconnected", 1000)
					.show();
			mBounded = false;
			mGpsLocation = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(LoginActivity.this, "Service is connected", 1000)
					.show();
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder) service;
			mGpsLocation = mLocalBinder.getServerInstance();

			// InternetConnection.checkConnection(getApplicationContext(), new
			// InternetConnectionContinue() {
			//
			// @Override
			// public void onResume() {
			// // TODO Auto-generated method stub
			// locationTask.execute(new Boolean[] { true });
			// }
			// });
			if (InternetConnection.isOnline(getApplicationContext())) {
				//locationTask.execute(new Boolean[] { true });
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
			String usernameFromEditText = ((EditText) findViewById(R.id.login_edittext_name))
					.getEditableText().toString();

			Log.e("LOGIN SCREEN - CATCHED USERNAME", usernameFromEditText);
			String passwordFromEditText = ((EditText) findViewById(R.id.login_edittext_password))
					.getEditableText().toString();
			Log.e("LOGIN SCREEN - CATCHED PASSWORD: ", passwordFromEditText);
			User actualUser = Session
					.getInstance()
					.getActualCommunicationInterface()
					.authenticationUser(usernameFromEditText,
							passwordFromEditText);
			if (actualUser == null) {
				Log.e("LOGIN SCREEN",
						"WRONG NAME-PASSWORD PAIR, TOAST WILL BE SHOWED");
				new ErrorToast(this,"Sikertelen bejelentkezés! Hibásfelhasználónév vagy jelszó! Próbáld újra!").show();
				Log.e("LOGIN SCREEN", "TOAST SHOWN SUCCESSFULLY");
			} else {
				// Ha a nĂ©v Ă©s jelszĂł pĂˇros helyes
				Log.e("LOGIN SCREEN",
						"CORRECT USERNAME-PASSWORD PAIR, CLUBSACTIVITY STARTING");
				// cityname = "Debrecen";
				mGpsLocation.onDestroy();
				locationTask.cancel(true);

				loginSynchronize(actualUser);
				Intent newIntent = new Intent(this, ClubsActivity.class);
				startActivity(newIntent);
				Log.e("LOGIN SCREEN", "CLUBSACTIVITY STARTED");
				finish();
			}
			break;

		case R.id.login_button_register:
			Log.e("REGISTER",
					"REGISTER BUTTON PRESSED, REGISTERACTIVITY STARTING");
			startActivity(new Intent(this, RegisterActivity.class));
			Log.e("REGISTER", "REGISTERACTIVITY STARTED");
		}
	}

	/*
	 * //EZ ĂŤGY SZAR!!!! NE HASZNĂ�LJA SENKI SEM! :) private String
	 * getMyCityName() { List<Address> resultArray = null; Geocoder geoc = new
	 * Geocoder(getApplicationContext()); try { resultArray =
	 * geoc.getFromLocation(clubArray.get(i).getLatlng().latitude,
	 * clubArray.get(i).getLatlng().longitude, 1); } catch (IOException e) {
	 * e.printStackTrace(); } String resultAddress = ""; if(resultArray.size() >
	 * 0) { resultAddress = resultArray.get(0).toString();
	 * Log.e("GETMYCITYNAME", "ADDRESS ELEMENT 0 " + resultAddress[0]); for(int
	 * j = 1; j < resultArray.get(i).toString()); Log.e("GETMYCITYNAME",
	 * "ADDRESS ELEMENT " + j + ": " + resultAddress[j]); } return
	 * resultAddress[0]; }
	 */

	void loginSynchronize(User actualUser) {
		Session.setActualUser(actualUser);
		// String cityname = "Debrecen";
		// GPSLocation locator = new GPSLocation();
		// while( ! locator.gotLocation() ) {
		// }
		// cityname = locator.getCityName();
		Log.e("LOGIN SYNCHRONIZE", "LOGIN IN PROGRESS, NAME OF ACTUAL CITY: "
				+ cityname);
		actualUser.favoriteClubs = Session.getInstance()
				.getActualCommunicationInterface()
				.getFavoriteClubsFromUserId(actualUser.getId());
		Log.e("LOGIN SYNCHRONIZE", "FAVOURITE CLUBS CATCHED");
		Session.setSearchViewClubs(Session.getInstance()
				.getActualCommunicationInterface()
				.getClubsFromCityName(cityname));
		Log.e("LOGIN SYNCHRONIZE", "CLUBS IN THE ACTUAL CITY (" + cityname
				+ ") CATCHED");
		Session.getActualUser().usersClubs = Session.getInstance()
				.getActualCommunicationInterface()
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

	/*
	 * public static class ErrorDialogFragment extends DialogFragment {
	 * 
	 * // Global field to contain the error dialog private Dialog mDialog; //
	 * Default constructor. Sets the dialog field to null public
	 * ErrorDialogFragment() { super(); mDialog = null; } // Set the dialog to
	 * display public void setDialog(Dialog dialog) { mDialog = dialog; } //
	 * Return a Dialog to the DialogFragment.
	 * 
	 * @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
	 * return mDialog; } }
	 */
	/*
	 * @Override protected void onActivityResult( int requestCode, int
	 * resultCode, Intent data) { // Decide what to do based on the original
	 * request code switch (requestCode) { case
	 * CONNECTION_FAILURE_RESOLUTION_REQUEST : // If the result code is
	 * Activity.RESULT_OK, try to connect again
	 * 
	 * switch (resultCode) { case Activity.RESULT_OK : //Try the request again
	 * 
	 * break; } } }
	 */
	/*
	 * private boolean servicesConnected() { // Check that Google Play services
	 * is available int resultCode = GooglePlayServicesUtil.
	 * isGooglePlayServicesAvailable(this); // If Google Play services is
	 * available if (ConnectionResult.SUCCESS == resultCode) { // In debug mode,
	 * log the status Log.d("Location Updates",
	 * "Google Play services is available."); // Continue return true; // Google
	 * Play services was not available for some reason } else { // Get the error
	 * code ConnectionResult cr = null; int errorCode = cr.getErrorCode(); //
	 * Get the error dialog from Google Play services Dialog errorDialog =
	 * GooglePlayServicesUtil.getErrorDialog( errorCode, this,
	 * CONNECTION_FAILURE_RESOLUTION_REQUEST);
	 * 
	 * // If Google Play services can provide an error dialog if (errorDialog !=
	 * null) { // Create a new DialogFragment for the error dialog
	 * ErrorDialogFragment errorFragment = new ErrorDialogFragment(); // Set the
	 * dialog in the DialogFragment errorFragment.setDialog(errorDialog); //
	 * Show the error dialog in the DialogFragment
	 * errorFragment.show(getSupportFragmentManager(), "Location Updates");
	 * 
	 * } } return false; }
	 */

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	// @Override
	// public void onConnectionFailed(ConnectionResult connectionResult) {
	// /*
	// * Google Play services can resolve some errors it detects.
	// * If the error has a resolution, try sending an Intent to
	// * start a Google Play services activity that can resolve
	// * error.
	// */
	// if (connectionResult.hasResolution()) {
	// try {
	// // Start an Activity that tries to resolve the error
	// connectionResult.startResolutionForResult(
	// this,
	// CONNECTION_FAILURE_RESOLUTION_REQUEST);
	// /*
	// * Thrown if Google Play services canceled the original
	// * PendingIntent
	// */
	// } catch (IntentSender.SendIntentException e) {
	// // Log the error
	// e.printStackTrace();
	// }
	// } else {
	// /*
	// * If no resolution is available, display a dialog to the
	// * user with the error.
	// */
	// showDialog(connectionResult.getErrorCode());
	// }
	// }

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	// @Override
	// public void onConnected(Bundle dataBundle) {
	// // Display the connection status
	// Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	//
	// }

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	// @Override
	// public void onDisconnected() {
	// // Display the connection status
	// Toast.makeText(this, "Disconnected. Please re-connect.",
	// Toast.LENGTH_SHORT).show();
	// }

	/*
	 * Called when the Activity becomes visible.
	 */
	// @Override
	// protected void onStart() {
	// super.onStart();
	// // Connect the client.
	// mLocationClient.connect();
	// }

	/*
	 * Called when the Activity is no longer visible.
	 */
	// @Override
	// protected void onStop() {
	// // Disconnecting the client invalidates it.
	// mLocationClient.disconnect();
	// super.onStop();
	// }
	//
}
