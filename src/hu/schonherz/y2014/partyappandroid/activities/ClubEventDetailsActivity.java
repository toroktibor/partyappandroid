package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ClubEventDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new SimpleActionBar(this, "Esemény részletei").setLayout();
	
	setContentView(R.layout.activity_club_event_details);
	int eventListPosition = getIntent().getExtras().getInt("listPosition");
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");

	Event actualEvent = Session.getSearchViewClubs().get(clubListPosition).events.get(eventListPosition);

	TextView nameTextView = (TextView) findViewById(R.id.club_event_details_textview_name);
	TextView dateTextView = (TextView) findViewById(R.id.club_event_details_textview_date);
	TextView musicTextView = (TextView) findViewById(R.id.club_event_details_textview_music);
	TextView descriprionTextView = (TextView) findViewById(R.id.club_event_details_textview_description);

	nameTextView.setText(actualEvent.name);
	dateTextView.setText(actualEvent.start_date);
	musicTextView.setText("Zenei stílus: " + actualEvent.music_type);
	descriprionTextView.setText(actualEvent.description);

    }

}
