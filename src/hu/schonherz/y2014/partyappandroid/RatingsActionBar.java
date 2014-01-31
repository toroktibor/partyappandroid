package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubRatingsActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity.SourceOfList;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity.SourceOfView;
import hu.schonherz.y2014.partyappandroid.activities.ClubsUpdateableFragment;
import hu.schonherz.y2014.partyappandroid.activities.LoginActivity;
import hu.schonherz.y2014.partyappandroid.activities.NewClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.PendingListActivity;
import hu.schonherz.y2014.partyappandroid.activities.ProfileActivity;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class RatingsActionBar{

    private final ClubRatingsActivity activity;

    public RatingsActionBar(ClubRatingsActivity activity) {
	this.activity = activity;
    }

    public void setLayout() {
	ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_ratings, null);
	ActionBar ab = activity.getSupportActionBar();

	ab.setDisplayShowHomeEnabled(false);
	ab.setDisplayShowTitleEnabled(false);
	ab.setDisplayShowCustomEnabled(true);
	ab.setCustomView(actionBarLayout);

    }

   
}
