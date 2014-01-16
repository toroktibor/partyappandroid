package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;

public class LoginActivity 	extends 	Activity 
							/*implements 	GooglePlayServicesClient.ConnectionCallbacks,
										GooglePlayServicesClient.OnConnectionFailedListener */{

    //private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	//LocationClient mLocationClient;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		if(Session.getInstance().getActualUser() != null) {
			loginSynchronize(Session.getInstance().getActualUser());
			Intent newIntent = new Intent(this, ClubsActivity.class);
			startActivity(newIntent);
			finish();
		}
		
		//mLocationClient = new LocationClient(this, this, this);
		//Location mCurrentLocation;
	    //mCurrentLocation = mLocationClient.getLastLocation();
	    //System.out.print("ITT VAGYUNK HÉÉÉJ!!!!!!!!!!!!!!: ");
	    //mCurrentLocation.toString();
    }


    public void onClickHandler(View v) {
    	switch (v.getId()) {
		case R.id.login_button_login:
			String usernameFromEditText = ((EditText) findViewById(R.id.login_edittext_name)).getEditableText().toString();
			//Log.i("Login screen - username: ", usernameFromEditText);
			String passwordFromEditText = ((EditText) findViewById(R.id.login_edittext_password)).getEditableText().toString();
			//Log.i("Login screen - password: ", passwordFromEditText);
			User actualUser = Session.getInstance().getActualCommunicationInterface().authenticationUser(usernameFromEditText, passwordFromEditText);
			if(actualUser == null) {
				LayoutInflater inflater = getLayoutInflater();
				View toastView = inflater.inflate(R.layout.toast_login_unsuccessful, (ViewGroup) findViewById(R.id.toast_login_unsuccessful_root));
				
				Toast unsuccesfullLogin = new Toast(getApplicationContext());
				unsuccesfullLogin.setGravity(Gravity.CENTER, 0, 0);
				unsuccesfullLogin.setDuration(Toast.LENGTH_LONG);
				unsuccesfullLogin.setView(toastView);
				unsuccesfullLogin.show();
			}
			else{
				// Ha a név és jelszó páros helyes
				loginSynchronize(actualUser);
				Intent newIntent = new Intent(this, ClubsActivity.class);
				startActivity(newIntent);
				finish();
			}
			break;

		case R.id.login_button_register:
			startActivity(new Intent(this, RegisterActivity.class));
		}

    }
    
    
    /*
    String getMyCityName() {
    	List<Address> resultArray = null;
		Geocoder geoc = new Geocoder(getApplicationContext());
		try {
			resultArray = geoc.getFromLocation(clubArray.get(i).getLatlng().latitude, clubArray.get(i).getLatlng().longitude, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resultAddress = "";
		if(resultArray.size() > 0) {
    		resultAddress = resultArray.get(0).toString();
			for(int j = 1; j < resultArray.size(); ++j) {
    			resultAddress += (" " + resultArray.get(i).toString());
    		}
    }
    */
    
    void loginSynchronize(User actualUser){
    	Session.setActualUser(actualUser);
    	String cityname = "Pl";  // itt kell lokális adatok beszerzése
    	//String cityname = getMyCityName();
    	actualUser.favoriteClubs = Session.getInstance().getActualCommunicationInterface().getFavoriteClubsFromUserId(actualUser.getId());
    	Session.setSearchViewClubs(Session.getInstance().getActualCommunicationInterface().getClubsFromCityName(cityname));
    	Session.getInstance().setSearchViewClubs(Session.getInstance().getActualCommunicationInterface().getClubsFromCityName(cityname));
    }
    
    User loginOnline(Context context, User actualUser) {
    	User result = null ;
    	return result;
    }
    
    User loginOffline(Context context, User actualUser) {
    	User result = null;
    	return result;
    }

    /*
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
*/
	/*
	@Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
            // If the result code is Activity.RESULT_OK, try to connect again
             
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    //Try the request again
                     
                    break;
                }
        }
     }
    */
	/*
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else { 
			// Get the error code
        	ConnectionResult cr = null;
            int errorCode = cr.getErrorCode();
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getSupportFragmentManager(),
                        "Location Updates");
                
            }
        }
        return false;
    }
    */

     /* Called by Location Services if the attempt to
     * Location Services fails.
     */
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        /*
//         * Google Play services can resolve some errors it detects.
//         * If the error has a resolution, try sending an Intent to
//         * start a Google Play services activity that can resolve
//         * error.
//         */
//        if (connectionResult.hasResolution()) {
//            try {
//                // Start an Activity that tries to resolve the error
//                connectionResult.startResolutionForResult(
//                        this,
//                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
//                /*
//                 * Thrown if Google Play services canceled the original
//                 * PendingIntent
//                 */
//            } catch (IntentSender.SendIntentException e) {
//                // Log the error
//                e.printStackTrace();
//            }
//        } else {
//            /*
//             * If no resolution is available, display a dialog to the
//             * user with the error.
//             */
//            showDialog(connectionResult.getErrorCode());
//        }
//    }

	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
//	@Override
//    public void onConnected(Bundle dataBundle) {
//        // Display the connection status
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//
//    }

	  /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
//    @Override
//    public void onDisconnected() {
//        // Display the connection status
//        Toast.makeText(this, "Disconnected. Please re-connect.",
//                Toast.LENGTH_SHORT).show();
//    }

    /*
     * Called when the Activity becomes visible.
     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Connect the client.
//        mLocationClient.connect();
//    }
 
    /*
     * Called when the Activity is no longer visible.
     */
//    @Override
//    protected void onStop() {
//        // Disconnecting the client invalidates it.
//        mLocationClient.disconnect();
//        super.onStop();
//    }
//    
}
