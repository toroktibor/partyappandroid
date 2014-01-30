package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PendingImageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_image);
		
		new SimpleActionBar(this, "Jóváhagyandó képek").setLayout();
		
	}

}
