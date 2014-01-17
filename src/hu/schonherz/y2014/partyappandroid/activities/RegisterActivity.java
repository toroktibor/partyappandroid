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

public class RegisterActivity extends FragmentActivity {

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

}
