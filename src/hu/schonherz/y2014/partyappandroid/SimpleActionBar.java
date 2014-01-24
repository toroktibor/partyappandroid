package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class SimpleActionBar implements OnClickListener {

    private ActionBarActivity activity;
    String title;

    public SimpleActionBar(ActionBarActivity activity, String title) {
	this.activity = activity;
	this.title=title;
    }

    public void setLayout() {
	ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_simple, null);
	
	
	
	ActionBar ab = activity.getSupportActionBar();
	ab.setDisplayShowHomeEnabled(false);
	ab.setDisplayShowTitleEnabled(false);
	ab.setDisplayShowCustomEnabled(true);
	ab.setCustomView(actionBarLayout);
		
	TextView tv = (TextView) activity.findViewById(R.id.actionbar_simple_textview_title);
	tv.setText(title);
	
    }

    @Override
    public void onClick(View v) {
	Intent i;
	switch (v.getId()) {
	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onClick view");
	    break;
	}

    }
}
