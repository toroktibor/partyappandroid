package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ClubInfoModifyActivity extends Activity {
	
	Club actualClub;
	EditText clubNameEditText;
	EditText clubAddressEditText;
	Spinner clubTypeSpinner;
	EditText clubPhonenumberEditText;
	EditText clubEmailEditText;
	EditText clubDescriptionEditText;
	ImageView clubImageView;
	Button clubModifyButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club_info_modify);
		
		int position = getIntent().getExtras().getInt("listPosition");
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
		clubImageView = (ImageView) findViewById(R.id.club_modify_activity_image_imageview);
		clubModifyButton = (Button) findViewById(R.id.club_modify_activity_modify_button);
		
		clubNameEditText.setText(actualClub.name);
		clubAddressEditText.setText(actualClub.address);
		clubTypeSpinner.setSelection(Club.getClubTypePosition(actualClub.type));
		clubPhonenumberEditText.setText(actualClub.phonenumber);
		clubEmailEditText.setText(actualClub.email);
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
				
				Session.getInstance().getActualCommunicationInterface().updateClubInfo(actualClub.id, actualClub.name, actualClub.type, actualClub.description, actualClub.address, actualClub.phonenumber, actualClub.email);
				finish();
			}
		});
	}

}
