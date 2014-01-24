package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ClubLocationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new SimpleActionBar(this, "Klub helye a térképen").setLayout();
	
	setContentView(R.layout.activity_club_location);
    }

}
