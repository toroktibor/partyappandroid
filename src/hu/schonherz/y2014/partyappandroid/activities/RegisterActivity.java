package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends ActionBarActivity implements
	hu.schonherz.y2014.partyappandroid.dialogs.DatePickerCommunicator {

    EditText nameEditText;
    EditText passwordEditText;
    EditText password2EditText;
    EditText emailEditText;
    EditText dateOfBirthEditText;
    Spinner spinnerSexSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	new SimpleActionBar(this, "Regisztráció").setLayout();

	setContentView(R.layout.activity_register);
	nameEditText = (EditText) findViewById(R.id.register_edittext_name);
	passwordEditText = (EditText) findViewById(R.id.register_edittext_password);
	password2EditText = (EditText) findViewById(R.id.register_edittext_password2);
	emailEditText = (EditText) findViewById(R.id.register_edittext_email);
	dateOfBirthEditText = (EditText) findViewById(R.id.register_edittext_dateofbirth);
	spinnerSexSpinner = (Spinner) findViewById(R.id.register_spinner_sex);
    }

    public void onClickHandler(View v) {
	if (v.getId() == R.id.register_button_register) {
	    final String userName = nameEditText.getText().toString();
	    final String userPassword = passwordEditText.getText().toString();
	    String userPassword2 = password2EditText.getText().toString();
	    final String userEmail = emailEditText.getText().toString();
	    final String userBirthday = dateOfBirthEditText.getText().toString();
	    String userSex = spinnerSexSpinner.getSelectedItem().toString();

	    if (userName.isEmpty()) {
		new ErrorToast(this, "Nem adtál meg nevet!").show();
		return;
	    }
	    if (userPassword.isEmpty()) {
		return;
	    }
	    if (!userPassword2.isEmpty()) {
		if (!userPassword.equals(userPassword2)) {
		    return;
		}
	    } else {
		return;
	    }
	    if (userEmail.isEmpty()) {
		return;
	    }
	    if (userBirthday.isEmpty()) {
		return;
	    }
	    if (userSex.isEmpty()) {
		return;
	    }
	    final int userSexInt = userSex.equals("Férfi") ? 0 : 1;
	    
	    Session.getInstance().progressDialog=ProgressDialog.show(this, "Kérlek várj", "Regisztráció folyamatban...", true, false);
	    new Thread(new Runnable() {

		@Override
		public void run() {
		    final User newUser = Session.getInstance().getActualCommunicationInterface()
			    .registerANewUser(userName, userPassword, userEmail, userSexInt, userBirthday);
		    RegisterActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
			    if (newUser == null) {
				return;
			    }
			    LoginActivity.loginSynchronize(newUser, getApplicationContext());
			    Intent newIntent = new Intent(RegisterActivity.this, ClubsActivity.class);
			    Session.getInstance().dismissProgressDialog();			   
			    if( LoginActivity.instance != null ){
				LoginActivity.instance.finish();
			    }
			    new DoneToast(RegisterActivity.this,"Sikeres regisztráció!").show();
			    startActivity(newIntent);
			    Log.e("REGISTER FORM", "CLUBSACTIVITY STARTED");
			    finish();			    
			}
		    });
		}
	    }).start();

	}
	if (v.getId() == R.id.register_edittext_dateofbirth) {

	    DialogFragment datepicker = new DatePickerFragment();
	    datepicker.show(getSupportFragmentManager(), "timePicker");

	}
    }

    @Override
    public void onDatePicked(String date) {
	dateOfBirthEditText.setText(date);
    }

}
