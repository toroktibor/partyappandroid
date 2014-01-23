package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsUpdateableFragment;
import hu.schonherz.y2014.partyappandroid.activities.LoginActivity;
import hu.schonherz.y2014.partyappandroid.activities.NewClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.PendingListActivity;
import hu.schonherz.y2014.partyappandroid.activities.ProfileActivity;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;

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
	activity.findViewById(R.id.actionbar_clubs_button_b).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_c).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_d).setOnClickListener(this);
	activity.findViewById(R.id.actionbar_clubs_button_e).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
	Intent i;
	switch (v.getId()) {
	
	
		case R.id.actionbar_clubs_button_b:
		    /* viewPager lapozása a lista nézetre */
		    activity.viewPager.setCurrentItem(0);
		    break;
		case R.id.actionbar_clubs_button_c:
		    /* viewPager lapozása a térkép nézetre */
		    InternetConnection.checkConnection(activity, new InternetConnectionContinue() {
			@Override
			public void onResume() {
			    activity.viewPager.setCurrentItem(1);
			}
		    });
		    break;
		case R.id.actionbar_clubs_button_d:
	
		    InternetConnection.checkConnection(activity, new InternetConnectionContinue() {
			@Override
			public void onResume() {
			    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
			    ViewGroup view = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.dialog_clubs_search,
				    null);
			    adb.setView(view);
			    

			    
			    final Dialog d = adb.create();			    
			    d.show();
			    
			    d.findViewById(R.id.dialog_clubs_search_button).setOnClickListener(new OnClickListener() {
			        
			        @Override
			        public void onClick(View v) {
			            
			            	EditText name = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_name);
			            	EditText type = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_type);
			            	EditText city = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_cityname);
			            	
			            	Log.i(this.getClass().getName(),"Eddigi találatok száma: "+Session.getSearchViewClubs().size());
			            	Log.i(this.getClass().getName(),"Keresés: név:["+name.getText().toString()+"] típus:["+type.getText().toString()+"] város:["+city.getText().toString()+"]");
			            			
			            	Session.getSearchViewClubs().clear();
			            	Session.getSearchViewClubs().addAll(Session.getInstance()
						.getActualCommunicationInterface()
						.getClubsFromCityName(city.getText().toString()));
			            	
			            	Log.i(this.getClass().getName(),"Keresési találatok száma: "+Session.getSearchViewClubs().size());
			            	
			            	ClubsUpdateableFragment cuf = (ClubsUpdateableFragment) ((ClubsActivity)activity).currentFragment;
			            	cuf.updateResults();
			            	
			    		d.cancel();
			    	
			        }
			    });
			}
		    });
	
		    break;
		case R.id.actionbar_clubs_button_e:
		    PopupMenu popupmenu = new PopupMenu(activity, v);
		    MenuItem item;
		    if (Session.getActualUser().getType() == 0) {
				item = popupmenu.getMenu().add(0, 1, 0, "Új hely hozzáadása");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 2, 0, "Kedvencek");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 3, 0, "Helyeim");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 4, 0, "Profilom");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 5, 0, "Kijelentkezés");
				item.setOnMenuItemClickListener(this);
		    } 
		    else if (Session.getActualUser().getType() == 1) {
				item = popupmenu.getMenu().add(0, 1, 0, "Új hely hozzáadása");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 2, 0, "Kedvencek");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 3, 0, "Helyeim");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 4, 0, "Profilom");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 5, 0, "Kijelentkezés");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 6, 0, "Jóváhagyások");
				item.setOnMenuItemClickListener(this);
		    }
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
	int clickedItemId = arg0.getItemId();
	switch (clickedItemId) {
		case 1: // ÚJ HELY HOZZÁADÁSA
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> ÚJ HELY HOZZÁADÁSA");
		    i = new Intent(activity, NewClubActivity.class);
		    Log.e("MAIN SCREEN", "STARTING NEWCLUBACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "NEWCLUBACTIVITY STARTED");
		    break;
	
		case 2: // KEDVENCEK
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> KEDVENCEK");
		    i = new Intent(activity, ClubsActivity.class);
		    Log.e("MAIN SCREEN", "USER'S FAVORITE CLUBS LOADING");
		    Session.setSearchViewClubs(Session.getActualUser().favoriteClubs);
		    Log.e("MAIN SCREEN", "STARTING CLUBSACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "CLUBSACTIVITY STARTED");
		    break;
	
		case 3: // HELYEIM
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> HELYEIM");
		    i = new Intent(activity, ClubsActivity.class);
		    Log.e("MAIN SCREEN", "USER'S OWN CLUBS LOADING");
		    Session.setSearchViewClubs(Session.getActualUser().usersClubs);
		    Log.e("MAIN SCREEN", "USER'S OWN CLUBS LOADED");
		    Log.e("MAIN SCREEN", "STARTING CLUBSACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "CLUBSACTIVITY STARTED");
		    break;
		case 4: // PROFILOM
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> PROFILOM");
		    i = new Intent(activity, ProfileActivity.class);
		    Log.e("MAIN SCREEN", "STARTING PROFILEACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "PROFILEACTIVITY STARTED");
		    break;
		case 5: // KIJELENTKEZÉS
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> KIJELENTKEZÉS");
		    Session.closeSession();
		    Log.e("MAIN SCREEN", "SESSION CLOSED");
		    i = new Intent(activity, LoginActivity.class);
		    Log.e("MAIN SCREEN", "STARTING LOGINACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "LOGINACTIVITY STARTED");
		    activity.finish();
		    break;
		case 6: // JÓVÁHAGYÁSOK (CSAK ADMINNAK)
			Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> JÓVÁHAGYÁSOK");
		    i = new Intent(activity, PendingListActivity.class);
		    Log.e("MAIN SCREEN", "STARTING PENDINGLISTACTIVITY");
		    activity.startActivity(i);
		    Log.e("MAIN SCREEN", "PENDINGLISTACTIVITY STARTED");
		    break;
		default:
		    Log.e("MAIN SCREEN", "POPUP MENU: NOT HANDLED onMenuItemClick");
	}

	return true;
    }
}