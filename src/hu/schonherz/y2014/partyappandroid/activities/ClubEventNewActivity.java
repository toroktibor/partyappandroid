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

public class ClubEventNewActivity extends Activity {
	
	ImageView imageView;
	EditText nameEditText;
	EditText dateEditText;
	EditText descriptionEditText;
	Button addButton;
	int clubListPosition;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_club_event_new);
	
	clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	
	imageView = (ImageView) findViewById(R.id.club_event_new_imageview);
	nameEditText = (EditText) findViewById(R.id.club_event_new_edittext_name);
	dateEditText = (EditText) findViewById(R.id.club_event_new_edittext_date);
	descriptionEditText = (EditText) findViewById(R.id.club_event_new_edittext_description);
	addButton = (Button) findViewById(R.id.club_event_new_button_new);
	
	addButton.setOnClickListener(new OnClickListener() {
		
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
			//kuldunk uzcsit szervernek
			Event newEvent = new Event(5, name, description, date, "vegyes", 1);
			Session.getSearchViewClubs().get(clubListPosition).events.add(newEvent);
			finish();
		}
	});
	
    }

}
