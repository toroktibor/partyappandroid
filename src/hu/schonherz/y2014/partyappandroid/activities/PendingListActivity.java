package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PendingListActivity extends ActionBarActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar actionBar = getSupportActionBar();
	actionBar.setDisplayShowTitleEnabled(false);
	setContentView(R.layout.activity_pending_list);
			
	actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	actionBar.setCustomView(R.layout.pending_list_actionbar);
	
	Button pendingClubsButton = (Button) findViewById(R.id.pendings_new_clubs_button);
	Button pendingRatingsButton = (Button) findViewById(R.id.pendings_new_rating_button);
	Button pendingImagesButton = (Button) findViewById(R.id.pendings_new_image_button);
	Button pendingOwnersButton = (Button) findViewById(R.id.pendings_owner_please_button);
	
	pendingClubsButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),PendingClubsListActivity.class);
			startActivity(i);		
		}
	});
	
	pendingOwnersButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),PendingOwnerRequest.class);
			startActivity(i);
		}
	});
	
    }
    
    @Override
    protected void onResume() {
    	TextView menuText = (TextView) findViewById(R.id.pending_list_actionbar_name);
    	menuText.setText("Jóváhagyások");
    	super.onResume();
    }

}
