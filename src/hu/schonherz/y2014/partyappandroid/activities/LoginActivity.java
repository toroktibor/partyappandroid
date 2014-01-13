package hu.schonherz.y2014.partyappandroid.activities;

import java.util.zip.Inflater;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.communication.CommunicationInterface;
import hu.schonherz.y2014.partyappandroid.util.communication.SillyCommunication;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
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

	private CommunicationInterface myCommunicationInterface = new SillyCommunication();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);

    }


    public void onClickHandler(View v) {
    	switch (v.getId()) {
		case R.id.login_button_login:
			String usernameFromEditText = ((EditText) findViewById(R.id.login_edittext_name)).getEditableText().toString();
			//Log.i("Login screen - username: ", usernameFromEditText);
			String passwordFromEditText = ((EditText) findViewById(R.id.login_edittext_password)).getEditableText().toString();
			//Log.i("Login screen - password: ", passwordFromEditText);
			User actualUser = this.myCommunicationInterface.authenticationUser(usernameFromEditText, passwordFromEditText);
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
			else
				startActivity(new Intent(this, ClubsActivity.class));
			break;

		case R.id.login_button_register:
			startActivity(new Intent(this, RegisterActivity.class));
		}

    }

}
