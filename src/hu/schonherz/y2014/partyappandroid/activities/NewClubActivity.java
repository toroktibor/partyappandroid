package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewClubActivity extends FragmentActivity implements SetServicesCommunicator{

    EditText newClubNameEditText;
    EditText newClubAddressEditText;
    Spinner newClubTypeSpinner;
    CheckBox newClubOwnerCheckBox;
    Button addButton;
    Context thisContext;
    List<String> services;

    @Override	// EBBEN A METÓDUSBAN KAPJUK MEG, HOGY MILYEN SZOLGÁLTATÁSOKAT ADOTT MEG A FELHASZNÁLÓ A DIALOGFRAGMENT-BEN
    public void onServicesSetted(List<String> services) {
    	Log.e("NEWCLUBACTIVITY", "LIST OF SERVICES FROM DIALOG CATCHED");
	   	this.services = services;
	   	Log.e("NEWCLUBACTIVITY","SERVICES ARRAY FROM SERVICES LIST DONE");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_new_club);
	thisContext = getApplicationContext();

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
		String newClubType = String.valueOf(newClubTypeSpinner.getSelectedItem());
		Boolean isOwner = newClubOwnerCheckBox.isChecked();

		if (newClubName.isEmpty()) {
		    Toast.makeText(thisContext, "Nem adtál meg nevet, ez baj, sűtgősed szedd össze magad.",
			    Toast.LENGTH_LONG).show();
		    return;
		}

		if (newClubAddress.isEmpty()) {
		    Toast.makeText(thisContext, "Már arra se emlékszel hol basztál be tegnap? Szép vagy...",
			    Toast.LENGTH_LONG).show();
		    return;
		}

		int owner_user_id = isOwner ? Session.getInstance().getActualUser().getId() : -1;

		Session.getInstance().getActualCommunicationInterface()
			.sendANewClubRequest(newClubName, newClubAddress, newClubType, owner_user_id, services);

		onBackPressed();
	    }
	});

	Button setServicesButton = (Button) findViewById(R.id.new_club_button_set_services);
	setServicesButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SetServicesOfClubFragment serviceSetterFragment = new SetServicesOfClubFragment();
			serviceSetterFragment.show(getSupportFragmentManager(), "SetServicesOfClub");
		}
	});
    }
}
