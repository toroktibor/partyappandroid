package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.communication.SillyCommunication;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		if(Session.getInstance().getActualUser() != null) {
			loginSynchronize(Session.getInstance().getActualUser());
			Intent newIntent = new Intent(this, ClubsActivity.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			
			startActivity(newIntent);
		}
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
				Log.i("Toast felfújva!!","EDDIG JÓ!");
				
				Toast unsuccesfullLogin = new Toast(getApplicationContext());
				unsuccesfullLogin.setGravity(Gravity.CENTER, 0, 0);
				unsuccesfullLogin.setDuration(Toast.LENGTH_LONG);
				unsuccesfullLogin.setView(toastView);
				unsuccesfullLogin.show();
			}
			else{
				loginSynchronize(actualUser);
				Intent newIntent = new Intent(this, ClubsActivity.class);
				newIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(newIntent);
			}
			break;

		case R.id.login_button_register:
			startActivity(new Intent(this, RegisterActivity.class));
		}

    }
    
    void loginSynchronize(User actualUser){
    	Session.setActualUser(actualUser);
    	String cityname = "Pl";  // itt kell lokális adatok beszerzése
    	actualUser.favoriteClubs = Session.getInstance().getActualCommunicationInterface().getFavoriteClubsFromUserId(actualUser.id);
    	Session.setSearchViewCLubs(Session.getInstance().getActualCommunicationInterface().getClubsFromCityName(cityname));
    }
    
    User loginOnline(Context context, User actualUser) {
    	User result = null ;
    	return result;
    }
    
    User loginOffline(Context context, User actualUser) {
    	User result = null;
    	return result;
    }

}
