package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

public class ClubGaleryActivity extends ActionBarActivity {    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_club_galery);
	
	ClubActionBar ab = new ClubActionBar(this);
	ab.setLayout();
    }

}
