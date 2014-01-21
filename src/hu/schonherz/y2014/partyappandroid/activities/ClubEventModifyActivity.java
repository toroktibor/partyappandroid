package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ClubEventModifyActivity extends Activity {

	ImageView imageView;
	EditText nameEditText;
	EditText dateEditText;
	EditText descriptionEditText;
	Button modifyButton;
	Event actualEvent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_club_event_modify);
	
	int eventListPosition = getIntent().getExtras().getInt("eventsListPosition");
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	actualEvent = Session.getSearchViewClubs().get(clubListPosition).events.get(eventListPosition);
	
	
	imageView = (ImageView) findViewById(R.id.club_event_modify_imageview);
	nameEditText = (EditText) findViewById(R.id.club_event_modify_edittext_name);
	dateEditText = (EditText) findViewById(R.id.club_event_modify_edittext_date);
	descriptionEditText = (EditText) findViewById(R.id.club_event_modify_edittext_description);
	modifyButton = (Button) findViewById(R.id.club_event_modify_button_modify);
	
	nameEditText.setText(actualEvent.name);
	dateEditText.setText(actualEvent.start_date);
	descriptionEditText.setText(actualEvent.description);
	
	modifyButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String name = nameEditText.getText().toString();
			String date = dateEditText.getText().toString();
			String description = descriptionEditText.getText().toString();
			
			if(name.isEmpty()){
				Toast.makeText(getApplicationContext(), "Nem adta meg az esemény nevét!", Toast.LENGTH_LONG).show();
				return;
			}
			if(date.isEmpty()){
				Toast.makeText(getApplicationContext(), "Nem adta meg az esemény dátumát!", Toast.LENGTH_LONG).show();
				return;
			}
			if(description.isEmpty()){
				Toast.makeText(getApplicationContext(), "Nem adta meg az esemény leírását!", Toast.LENGTH_LONG).show();
				return;
			}
			//kuldunk uzenetet a szervernek
			actualEvent.name=name;
			actualEvent.start_date=date;
			actualEvent.description=description;
			finish();
		}
	});
	
    }

}
