package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubInfoFragment;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsListFragment;
import hu.schonherz.y2014.partyappandroid.activities.ClubsMapFragment;
import hu.schonherz.y2014.partyappandroid.activities.LoginActivity;
import hu.schonherz.y2014.partyappandroid.activities.NewClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ProfileActivity;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.MenuCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ClubsActionBar implements OnClickListener, OnMenuItemClickListener {

    private final ClubsActivity activity;

    public ClubsActionBar(ClubsActivity activity) {
    	this.activity = activity;
    }

    public void setLayout() {
	ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_clubs, null);
	ActionBar ab = activity.getSupportActionBar();
	ab.setDisplayShowHomeEnabled(false);
	ab.setDisplayShowTitleEnabled(false);
	ab.setDisplayShowCustomEnabled(true);
	ab.setCustomView(actionBarLayout);

	activity.findViewById(R.id.actionbar_clubs_button_a).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_b).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_c).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_d).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_e).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
	Intent i;
	switch (v.getId()) {
	case R.id.actionbar_clubs_button_a:
	    // TODO: Logout
		Session.closeSession();
		Intent la = new Intent(activity, LoginActivity.class);
	    activity.startActivity(la);
	    activity.finish();
	    break;

	case R.id.actionbar_clubs_button_b:
		/* viewPager lapozása a lista nézetre */
		//InternetConnection.checkConnection(activity);
		
		activity.viewPager.setCurrentItem(0);
	    break;

	case R.id.actionbar_clubs_button_c:
		/* viewPager lapozása a térkép nézetre */
		InternetConnection.checkConnection(activity,new InternetConnectionContinue() {
			
			@Override
			public void onResume() {
				activity.viewPager.setCurrentItem(1);
			}
		});
		
		
		
	    break;
	case R.id.actionbar_clubs_button_d:

	    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
	    ViewGroup view = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.dialog_clubs_search, null);
	    adb.setView(view);
	    adb.show();

	    break;

	case R.id.actionbar_clubs_button_e:
	    PopupMenu popupmenu = new PopupMenu(activity, v);
	    MenuItem item;

	    item = popupmenu.getMenu().add(0, 1, 0, "Új hely hozzáadása");
	    item.setOnMenuItemClickListener(this);
	    item = popupmenu.getMenu().add(0, 2, 0, "Profilom");
	    item.setOnMenuItemClickListener(this);
	    item = popupmenu.getMenu().add(0, 3, 0, "Egy szórakozóhely");
	    item.setOnMenuItemClickListener(this);

	    popupmenu.show();
	    break;

	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onClick view");
	    break;
	}

    }

    @Override
    public boolean onMenuItemClick(MenuItem arg0) {
	Intent i;
	switch (arg0.getItemId()) {
	case 1:
	    i = new Intent(activity, NewClubActivity.class);
	    activity.startActivity(i);
	    break;
	    
	case 2:
	    i = new Intent(activity, ProfileActivity.class);
	    activity.startActivity(i);
	    break;
	    
	case 3:
	    i = new Intent(activity, ClubActivity.class);
	    activity.startActivity(i);
	    break;
	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onMenuItemClick");
	}

	return true;
    }
}
