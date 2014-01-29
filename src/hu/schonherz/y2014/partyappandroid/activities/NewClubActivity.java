package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewClubActivity extends ActionBarActivity implements SetServicesCommunicator {

    EditText newClubNameEditText;
    EditText newClubAddressEditText;
    Spinner newClubTypeSpinner;
    CheckBox newClubOwnerCheckBox;
    Button addButton;
    Context thisContext;
    List<String> services;
    int selectedServicesNumber;
    TextView selectedServicesTextView;

    @Override
    // EBBEN A METÓDUSBAN KAPJUK MEG, HOGY MILYEN SZOLGÁLTATÁSOKAT ADOTT MEG A
    // FELHASZNÁLÓ A DIALOGFRAGMENT-BEN
    public void onServicesSetted(List<String> services) {
	Log.e("NEWCLUBACTIVITY", "LIST OF SERVICES FROM DIALOG CATCHED");
	this.services = services;
	selectedServicesTextView.setText(selectedServicesNumber + " szolgáltatás kiválasztva");
	for (int i = 0; i < this.services.size(); ++i)
	    Log.e("NEWCLUBACTIVITY", "CATCHED SERVICES: " + services.get(i));
	Log.e("NEWCLUBACTIVTY", "ONSERVICESETTED FINISHED");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	new SimpleActionBar(this, "Új klub hozzáadása").setLayout();

	setContentView(R.layout.activity_new_club);
	thisContext = getApplicationContext();

	newClubNameEditText = (EditText) findViewById(R.id.new_club_edittext_name);
	newClubAddressEditText = (EditText) findViewById(R.id.new_club_edittext_address);
	newClubTypeSpinner = (Spinner) findViewById(R.id.new_club_spinner_type);
	newClubOwnerCheckBox = (CheckBox) findViewById(R.id.new_club_checkbox_add);
	addButton = (Button) findViewById(R.id.new_club_button_add);
	selectedServicesTextView = (TextView) findViewById(R.id.new_club_selected_services_number_textview);
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
		Log.e("NEWCLUBACTIVITY", "NEW CLUB REQUEST SENT");
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
