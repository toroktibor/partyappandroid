package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubsActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ClubsListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_clubs_list);

	ClubsActionBar ab = new ClubsActionBar(this);
	ab.setLayout();
    }

}
