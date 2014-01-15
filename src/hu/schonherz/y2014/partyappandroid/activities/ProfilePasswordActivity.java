package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilePasswordActivity extends Activity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile_password);

	user = Session.getActualUser();
    }

    public void onClickHandler(View v) {
	switch (v.getId()) {
	case R.id.profile_password_button_save:
	    EditText oldPassword = (EditText) findViewById(R.id.profile_password_edittext_oldpassword);
	    EditText newPassword = (EditText) findViewById(R.id.profile_password_edittext_newpassword);
	    EditText newPasswordAgain = (EditText) findViewById(R.id.profile_password_edittext_newpasswordagain);

	    if (oldPassword.getText().toString().isEmpty() || newPassword.getText().toString().isEmpty()
		    || newPasswordAgain.getText().toString().isEmpty()) {
		Toast.makeText(this, "Minden mező kitöltése kötelező", Toast.LENGTH_LONG).show();
		return;
	    }

	    if (!oldPassword.getText().toString().equals(user.getPassword())) {
		Toast.makeText(this, "A megadott régi jelszó nem megfelelő!", Toast.LENGTH_LONG).show();
		return;
	    }
	    
	    if( ! newPassword.getText().toString().equals( newPasswordAgain.getText().toString() ) ){
		Toast.makeText(this, "A megadott új jelszavak nem egyeznek!", Toast.LENGTH_LONG).show();
		return;
	    }
	    
	    try {
		user.modifyPassword(newPassword.getText().toString());		
	    } catch (Exception e) {
		Toast.makeText(this, "A jelszómódosítás sikertelen!", Toast.LENGTH_LONG).show();
	    }
	    
	    Toast.makeText(this, "Sikeres jelszómódosítás", Toast.LENGTH_LONG).show();
	    
	    finish();
	    break;
	}
    }

}
