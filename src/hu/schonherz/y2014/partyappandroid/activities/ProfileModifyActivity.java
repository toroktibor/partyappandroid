package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfileModifyActivity extends Activity {

    private User user;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDateOfBirth;
    private Spinner spinnerSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile_modify);

	user = Session.getActualUser();

	editTextName = (EditText) findViewById(R.id.profile_modify_edittext_name);
	editTextName.setText(user.getNickname());

	editTextEmail = (EditText) findViewById(R.id.profile_modify_edittext_email);
	editTextEmail.setText(user.getEmail());

	editTextDateOfBirth = (EditText) findViewById(R.id.profile_modify_edittext_dateofbirth);
	editTextDateOfBirth.setText(user.getBirthday());

	spinnerSex = (Spinner) findViewById(R.id.profile_modify_spinner_sex);
	spinnerSex.setSelection(user.getSex());
    }

    public void onClickHandler(View v) {
	switch (v.getId()) {
	case R.id.profile_modify_button_save:

	    try {
		user.modifyUserData(editTextEmail.getText().toString(), editTextDateOfBirth.getText().toString(),
			spinnerSex.getSelectedItemPosition());
		new DoneToast(this, "Adatok sikeresen módosítva").show();
	    } catch (Exception e) {
		new ErrorToast(this, "Az adatok módosítása nem sikerült!").show();
	    }

	    finish();
	    break;
	}
    }

}
