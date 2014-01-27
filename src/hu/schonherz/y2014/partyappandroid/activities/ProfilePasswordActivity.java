package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class ProfilePasswordActivity extends ActionBarActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	new SimpleActionBar(this, "Jelszó módosítása").setLayout();

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
		new ErrorToast(this, "Minden mező kitöltése kötelező").show();
		return;
	    }

	    if (!oldPassword.getText().toString().equals(user.getPassword())) {
		new ErrorToast(this, "A megadott régi jelszó nem megfelelő!").show();
		return;
	    }

	    if (!newPassword.getText().toString().equals(newPasswordAgain.getText().toString())) {
		new ErrorToast(this, "A megadott új jelszavak nem egyeznek!").show();
		return;
	    }

	    try {
		user.modifyPassword(newPassword.getText().toString());
	    } catch (Exception e) {
		new ErrorToast(this, "A jelszómódosítás sikertelen!").show();
	    }

	    new DoneToast(this, "Sikeres jelszómódosítás").show();

	    finish();
	    break;
	}
    }

}
