package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ClubInfoModifyActivity extends ActionBarActivity implements SetServicesCommunicator {
	
	Club actualClub;
	EditText clubNameEditText;
	EditText clubAddressEditText;
	Spinner clubTypeSpinner;
	EditText clubPhonenumberEditText;
	EditText clubEmailEditText;
	EditText clubDescriptionEditText;
	ImageView clubImageView;
	Button clubModifyButton;
	private EditText clubServicesEditText;
	private ArrayList<String> selectedServices;
    private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new SimpleActionBar(this, "Hely adatainak módosítása").setLayout();
		
		setContentView(R.layout.activity_club_info_modify);
		
		position = getIntent().getExtras().getInt("listPosition");
		actualClub = Session.getSearchViewClubs().get(position);
		
		if(actualClub.isNotFullDownloaded()){
			actualClub.downloadEverything();
		}
		
		clubNameEditText = (EditText) findViewById(R.id.club_modify_activity_name_edittext);
		clubAddressEditText = (EditText) findViewById(R.id.club_modify_activity_address_edittext);
		clubTypeSpinner = (Spinner) findViewById(R.id.club_modify_activity_type_spinner);
		clubPhonenumberEditText = (EditText) findViewById(R.id.club_modify_activity_phonenumber_edittext);
		clubEmailEditText = (EditText) findViewById(R.id.club_modify_activity_email_edittext);
		clubDescriptionEditText = (EditText) findViewById(R.id.club_modify_activity_description_edittext);
		clubModifyButton = (Button) findViewById(R.id.club_modify_activity_modify_button);
		clubServicesEditText = (EditText) findViewById(R.id.club_modify_set_services);
		
		selectedServices = new ArrayList<String>();
		selectedServices.addAll(actualClub.services);
		
		clubServicesEditText.setText(SetServicesOfClubFragment.joinStrings( SetServicesOfClubFragment.tokenToName(selectedServices), ", "));
		
		clubServicesEditText.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			SetServicesOfClubFragment serviceSetterFragment = new SetServicesOfClubFragment();
			serviceSetterFragment.show(ClubInfoModifyActivity.this, selectedServices, getSupportFragmentManager(),
				"SetServicesWhenModify");
		    }
		});
		
		clubNameEditText.setText(actualClub.name);
		clubAddressEditText.setText(actualClub.address);
		clubTypeSpinner.setSelection(Club.getClubTypePosition(actualClub.type));
		if(!actualClub.phonenumber.equals("null"))
			clubPhonenumberEditText.setText(actualClub.phonenumber);
		if(!actualClub.email.equals("null"))
			clubEmailEditText.setText(actualClub.email);
		if(!actualClub.description.equals("null"))
			clubDescriptionEditText.setText(actualClub.description);
		
		clubModifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String clubName = clubNameEditText.getText().toString();
				String clubAddress = clubAddressEditText.getText().toString();
				String clubType = clubTypeSpinner.getSelectedItem().toString();
				String clubPhonenumber = clubPhonenumberEditText.getText().toString();
				String clubEmail = clubEmailEditText.getText().toString();
				String clubDescription = clubDescriptionEditText.getText().toString();
				
				if(clubName.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "Nem adtál meg nevet!", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				if(clubAddress.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "Nem adtál meg címet!", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				
				actualClub.name=clubName;
				actualClub.address=clubAddress;
				actualClub.type=clubType;
				actualClub.phonenumber=clubPhonenumber;
				actualClub.email=clubEmail;
				actualClub.description = clubDescription;
				
				Session.getInstance().progressDialog=ProgressDialog.show(ClubInfoModifyActivity.this, "Kérlek várj", "Módosítás folyamatban...", true, false);
				new NetThread(ClubInfoModifyActivity.this,new Runnable() {
                                    
                                    @Override
                                    public void run() {
                                        Session.getInstance().setPosition(ClubInfoModifyActivity.this, position);                                        
                                        Session.getInstance().getActualCommunicationInterface().updateClubInfo(actualClub.id, actualClub.name, actualClub.type, actualClub.description, actualClub.address, actualClub.phonenumber, actualClub.email);
                                        Session.getInstance().getActualCommunicationInterface().setServices(actualClub.id, selectedServices);
                                        actualClub.downloadBasicInfo();
                                        
                                        ClubInfoModifyActivity.this.runOnUiThread(new Runnable() {
                                            
                                            @Override
                                            public void run() {
                                                
                                                new DoneToast(ClubInfoModifyActivity.this,"Szórakozóhely adatai módosítva lettek!").show();
                                                Session.getInstance().dismissProgressDialog();
                                                finish();
                                                
                                            }
                                        });
                                        
                                    }
                                }).start();

			}
		});
	}

	@Override
	public void onServicesSetted(String result) {
	    clubServicesEditText.setText(result);
	}

}
