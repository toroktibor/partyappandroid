package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewClubActivity extends Activity {

	EditText newClubNameEditText;
	EditText newClubAddressEditText;
	Spinner newClubTypeSpinner;
	CheckBox newClubOwnerCheckBox;
	Button addButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_new_club);
	
	newClubNameEditText = (EditText) findViewById(R.id.new_club_edittext_name);
	newClubAddressEditText = (EditText) findViewById(R.id.new_club_edittext_address);
	newClubTypeSpinner = (Spinner) findViewById(R.id.new_club_spinner_type);
	newClubOwnerCheckBox = (CheckBox) findViewById(R.id.new_club_checkbox_add);
	addButton = (Button) findViewById(R.id.new_club_button_add);
	
	addButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			String newClubName = newClubNameEditText.getText().toString();
			String newClubAddress = newClubAddressEditText.getText().toString();
			//String newClubType = newClubTypeSpinner.getT
			
		}
	});
	
	Toast.makeText(this, "You fucked!", Toast.LENGTH_LONG).show();
			
    }

}
