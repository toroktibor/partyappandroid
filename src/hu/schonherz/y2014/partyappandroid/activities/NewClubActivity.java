package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

    private List<String> selectedServices = new ArrayList<String>();
    //private static List<Integer> checkBoxes = new ArrayList<Integer>();
    private EditText selectedServicesEditText;

    @Override
    // EBBEN A METÓDUSBAN KAPJUK MEG, HOGY MILYEN SZOLGÁLTATÁSOKAT ADOTT MEG A
    // FELHASZNÁLÓ A DIALOGFRAGMENT-BEN
    public void onServicesSetted(String servStrings) {
	selectedServicesEditText.setText(servStrings);
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
	selectedServicesEditText = (EditText) findViewById(R.id.new_club_button_set_services);
	Log.e("NEWCLUBACTIVITY", "VIEWS CATCHED FROM DIALOG");
	addButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		Log.e("NEWCLUBACTIVITY", "ADD BUTTON ONCLKLSTNR RUNS");
		final String newClubName = newClubNameEditText.getText().toString();
		final String newClubAddress = newClubAddressEditText.getText().toString();
		final String newClubType = String.valueOf(newClubTypeSpinner.getSelectedItem());
		Boolean isOwner = newClubOwnerCheckBox.isChecked();

		if (newClubName.isEmpty()) {
		    Toast.makeText(thisContext, "Nem adtál meg nevet! Sürgősen szedd össze magad.", Toast.LENGTH_LONG)
			    .show();
		    return;
		}

		if (newClubAddress.isEmpty()) {
		    Toast.makeText(thisContext, "Már arra se emlékszel hol b@sztál be tegnap? Szép vagy...",
			    Toast.LENGTH_LONG).show();
		    return;
		}

		final int owner_user_id = isOwner ? Session.getInstance().getActualUser().getId() : -1;

		Session.getInstance().progressDialog=ProgressDialog.show(NewClubActivity.this, "Kérlek várj", "Hozzáadás folyamatban...", true, false);
		    
		new Thread(new Runnable() {
		    
		    @Override
		    public void run() {
			Session.getInstance().getActualCommunicationInterface()
			.sendANewClubRequest(newClubName, newClubAddress, newClubType, owner_user_id, selectedServices);

			NewClubActivity.this.runOnUiThread(new Runnable() {
			    
			    @Override
			    public void run() {
				Session.getInstance().dismissProgressDialog();
				finish();
			    }
			});
			
		    }
		}).start();
		
				Log.e("NEWCLUBACTIVITY", "NEW CLUB REQUEST SENT");
		
	    }
	});

	selectedServicesEditText.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		SetServicesOfClubFragment serviceSetterFragment = new SetServicesOfClubFragment();
		serviceSetterFragment.show(NewClubActivity.this, selectedServices, getSupportFragmentManager(),
			"SetServicesOfClub");

	    }
	});

    }
}
