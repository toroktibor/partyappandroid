package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ClubEventNewActivity extends ActionBarActivity {

    EditText nameEditText;
    EditText dateEditText;
    EditText descriptionEditText;
    Button addButton;
    Spinner musicTypeSpinner;
    int clubListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new SimpleActionBar(this, "Esemény módosítása").setLayout();
	
	setContentView(R.layout.activity_club_event_new);

	clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");

	nameEditText = (EditText) findViewById(R.id.club_event_new_edittext_name);
	dateEditText = (EditText) findViewById(R.id.club_event_new_edittext_date);
	descriptionEditText = (EditText) findViewById(R.id.club_event_new_edittext_description);
	addButton = (Button) findViewById(R.id.club_event_new_button_new);
	musicTypeSpinner = (Spinner) findViewById(R.id.club_event_new_music_type_spinner);

	addButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		String name = nameEditText.getText().toString();
		String date = dateEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		String musicType = musicTypeSpinner.getSelectedItem().toString();

		if (name.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény nevét!", Toast.LENGTH_LONG).show();
		    return;
		}
		if (date.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény dátumát!", Toast.LENGTH_LONG)
			    .show();
		    return;
		}
		if (description.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény leírását!", Toast.LENGTH_LONG)
			    .show();
		    return;
		}
		int eventId = Session.getInstance().getActualCommunicationInterface().addEvent(Session.getSearchViewClubs().get(clubListPosition).id, name, description, date, "", musicType);
		Event newEvent = new Event(eventId, name, description, date, musicType, 1);
		Session.getSearchViewClubs().get(clubListPosition).events.add(newEvent);

		finish();
	    }
	});

    }

}
