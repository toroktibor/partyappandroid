package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends FragmentActivity implements hu.schonherz.y2014.partyappandroid.dialogs.DatePickerCommunicator {

	EditText nameEditText;
	EditText passwordEditText;
	EditText password2EditText;
	EditText emailEditText;
	EditText dateOfBirthEditText;
	Spinner spinnerSexSpinner;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
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
    		
    		finish();
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
