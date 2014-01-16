package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ClubActionBar implements OnClickListener, OnMenuItemClickListener {

    private ClubActivity activity;

    public ClubActionBar(ClubActivity activity) {
	this.activity = activity;
    }

    public void setLayout() {
	ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_club, null);
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

	case R.id.actionbar_club_button_e:
	    PopupMenu popupmenu = new PopupMenu(activity, v);
	    MenuItem item;

	    item = popupmenu.getMenu().add(0, 1, 0, "Árlista");
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
	    i = new Intent(activity, ClubMenuActivity.class);
	    activity.startActivity(i);
	    break;
	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onMenuItemClick");
	}

	return true;
    }
}
