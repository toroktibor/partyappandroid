package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsListFragment;
import hu.schonherz.y2014.partyappandroid.activities.ClubsMapFragment;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ClubActionBar implements OnClickListener {

    private ClubActivity activity;
    
    public ClubActionBar(ClubActivity activity){
	this.activity=activity;
    }
    
    public void setLayout(){
	ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_club,null);
	ActionBar ab = activity.getSupportActionBar();
	ab.setDisplayShowHomeEnabled(false);
	ab.setDisplayShowTitleEnabled(false);	
	ab.setDisplayShowCustomEnabled(true);
	ab.setCustomView(actionBarLayout);
	
	activity.findViewById(R.id.actionbar_club_button_a).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_club_button_b).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_club_button_c).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_club_button_d).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_club_button_e).setOnClickListener(this);
	
    }

    @Override
    public void onClick(View v) {
	Intent i;
	switch (v.getId()) {
	case R.id.actionbar_club_button_b:
		activity.viewPager.setCurrentItem(0);
		break;
	    
	case R.id.actionbar_club_button_c:
		activity.viewPager.setCurrentItem(1);
		break;
		
	case R.id.actionbar_club_button_d:
		activity.viewPager.setCurrentItem(2);
	    break;	

	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onClick view");
	    break;
	}
	
    }
}
