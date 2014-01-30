package hu.schonherz.y2014.partyappandroid;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.ac;

import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity.SourceOfView;
import hu.schonherz.y2014.partyappandroid.activities.ClubsUpdateableFragment;
import hu.schonherz.y2014.partyappandroid.activities.LoginActivity;
import hu.schonherz.y2014.partyappandroid.activities.NewClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.PendingListActivity;
import hu.schonherz.y2014.partyappandroid.activities.ProfileActivity;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;
import hu.schonherz.y2014.partyappandroid.activities.ClubsActivity.SourceOfList;
import hu.schonherz.y2014.partyappandroid.dialogs.SetServicesOfClubFragment;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnection;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ClubsActionBar implements OnClickListener, OnMenuItemClickListener, SetServicesCommunicator {

    private final ClubsActivity activity;
    private static int numberOfSelectedServices = 0;
    private static List<String> selectedServices = new ArrayList<String>();
    private TextView textViewSelectedServicesNumber;
    Integer[] icons = { R.id.checkBoxBilliard, R.id.checkBoxBowling, R.id.checkBoxCoctailBar, R.id.checkBoxDance,
	    R.id.checkBoxDarts, R.id.checkBoxDJ, R.id.checkBoxFnDControl, R.id.checkBoxLiveMusic, R.id.checkBoxMenu,
	    R.id.checkBoxSportTV, R.id.checkBoxWiFi };
    List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    
    
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

	((ImageView) activity.findViewById(R.id.actionbar_clubs_button_b)).setBackgroundDrawable(activity
		.getResources().getDrawable(R.drawable.ab_selected));
	((ImageView) activity.findViewById(R.id.actionbar_clubs_button_c)).setBackgroundDrawable(null);

    }

    @Override
    public void onClick(View v) {
	Intent i;
	switch (v.getId()) {
	case R.id.actionbar_clubs_button_a:
	    PopupMenu popupmenu = new PopupMenu(activity, v);
	    MenuItem item;

	    item = popupmenu.getMenu().add(0, 7, 0, "Közelben lévő klubok");
	    item.setOnMenuItemClickListener(this);
	    item = popupmenu.getMenu().add(0, 2, 0, "Kedvenceim");
	    item.setOnMenuItemClickListener(this);
	    item = popupmenu.getMenu().add(0, 3, 0, "Saját klubjaim");
	    item.setOnMenuItemClickListener(this);

	    popupmenu.show();
	    break;

	case R.id.actionbar_clubs_button_b:
	    /* viewPager lapozása a lista nézetre */
		ClubsActivity.sourceOfView=SourceOfView.LIST;
	    activity.viewPager.setCurrentItem(0);
	    ((ImageView) activity.findViewById(R.id.actionbar_clubs_button_b)).setBackgroundDrawable(activity
		    .getResources().getDrawable(R.drawable.ab_selected));
	    ((ImageView) activity.findViewById(R.id.actionbar_clubs_button_c)).setBackgroundDrawable(null);
	    break;
	case R.id.actionbar_clubs_button_c:
	    /* viewPager lapozása a térkép nézetre */
		ClubsActivity.sourceOfView=SourceOfView.MAP;
	    InternetConnection.checkConnection(activity, new InternetConnectionContinue() {
		@Override
		public void onResume() {
		    activity.viewPager.setCurrentItem(1);
		    ((ImageView) activity.findViewById(R.id.actionbar_clubs_button_c)).setBackgroundDrawable(activity
			    .getResources().getDrawable(R.drawable.ab_selected));
		    ((ImageView) activity.findViewById(R.id.actionbar_clubs_button_b)).setBackgroundDrawable(null);

		}
	    });
	    break;
	case R.id.actionbar_clubs_button_d: // KERESÉS

	    InternetConnection.checkConnection(activity, new InternetConnectionContinue() {

		@Override
		public void onResume() {
		    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
		    ViewGroup view = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.dialog_clubs_search,
			    null);
		    adb.setView(view);
		   
		    final Dialog d = adb.create();
		    d.show();
		    
		    textViewSelectedServicesNumber = (TextView) d
			    .findViewById(R.id.dialog_club_search_textview_number_of_selected_services);

		    for(int i = 0; i < Session.getInstance().servicesTokenList.size(); ++i) {
			checkBoxes.add((CheckBox) d.findViewById(icons[i]));
		    }
		    for(int i = 0; i < selectedServices.size(); ++i) {
			if(Session.getInstance().servicesTokenList.contains(selectedServices.get(i)))
			checkBoxes.get(Session.getInstance().servicesTokenList.
				indexOf(selectedServices.get(i))).setChecked(true);
		    }
		    
		    Button servicesButton = (Button) d.findViewById(R.id.dialog_club_search_button_set_services);

		    servicesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    SetServicesOfClubFragment serviceSetterFragment = new SetServicesOfClubFragment();
			    serviceSetterFragment.show(ClubsActionBar.this, activity.getSupportFragmentManager(), "SetServicesOfClub");
			}
		    });
		    d.findViewById(R.id.dialog_clubs_search_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			    EditText name = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_name);
			    EditText type = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_type);
			    final EditText city = (EditText) d.findViewById(R.id.dialog_clubs_search_edittext_cityname);

			    Log.i(this.getClass().getName(), "Eddigi találatok száma: "
				    + Session.getSearchViewClubs().size());
			    Log.i(this.getClass().getName(), "Keresés: név:[" + name.getText().toString() + "] típus:["
				    + type.getText().toString() + "] város:[" + city.getText().toString() + "]");

			    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
				    "Szórakozóhelyek keresése...", true, false);
			    new Thread(new Runnable() {

				@Override
				public void run() {

				    Session.getSearchViewClubs().clear();
				    Session.getSearchViewClubs().addAll(
					    Session.getInstance().getActualCommunicationInterface()
						    .getClubsFromCityName(city.getText().toString()));
				    // Geocoder send the latlng position of the
				    // places.
				    Session.getInstance().setPositions(activity);

				    Log.i(this.getClass().getName(), "Keresési találatok száma: "
					    + Session.getSearchViewClubs().size());

				    activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
					    ((ClubsUpdateableFragment) activity.fragments[0]).updateResults();
					    ((ClubsUpdateableFragment) activity.fragments[1]).updateResults();
					    ClubsActivity.sourceOfList = SourceOfList.SEARCH;
					    // Legszélső szűrő ikon beállítása keresésre.
					    d.cancel();
					    Session.getInstance().dismissProgressDialog();

					}
				    });

				}
			    }).start();

			}
		    });
		}
	    });

	    break;
	case R.id.actionbar_clubs_button_e:
	    PopupMenu popupmenu2 = new PopupMenu(activity, v);

	    MenuItem item2;

	    /*
	     * item = popupmenu.getMenu().add(0, 7, 0, "Hozzám közeli klubok");
	     * item.setOnMenuItemClickListener(this);
	     */
	    item2 = popupmenu2.getMenu().add(0, 1, 0, "Új hely hozzáadása");
	    item2.setOnMenuItemClickListener(this);
	    /*
	     * item = popupmenu.getMenu().add(0, 2, 0, "Kedvencek");
	     * item.setOnMenuItemClickListener(this);
	     */
	    /*
	     * item = popupmenu.getMenu().add(0, 3, 0, "Helyeim");
	     * item.setOnMenuItemClickListener(this);
	     */
	    item2 = popupmenu2.getMenu().add(0, 4, 0, "Profilom");
	    item2.setOnMenuItemClickListener(this);
	    item2 = popupmenu2.getMenu().add(0, 5, 0, "Kijelentkezés");
	    item2.setOnMenuItemClickListener(this);

	    if (Session.getActualUser().getType() == 1) {
		item2 = popupmenu2.getMenu().add(0, 6, 0, "Jóváhagyások");
		item2.setOnMenuItemClickListener(this);

	    }

	    popupmenu2.show();
	    break;
	default:
	    Log.e(this.getClass().getName(), "Nem kezelt onClick view");
	    break;
	}
    }

    @Override
    public boolean onMenuItemClick(MenuItem arg0) {
	Intent i;
	ImageView ib;
	int clickedItemId = arg0.getItemId();
	switch (clickedItemId) {
	case 1: // ÚJ HELY HOZZÁADÁSA
	    Log.e("MAIN SCREEN", "POPUPMENU ITEM #" + clickedItemId + " CLICKED -> ÚJ HELY HOZZÁADÁSA");
	    i = new Intent(activity, NewClubActivity.class);
	    Log.e("MAIN SCREEN", "STARTING NEWCLUBACTIVITY");
	    activity.startActivity(i);
	    Log.e("MAIN SCREEN", "NEWCLUBACTIVITY STARTED");
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
	    activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
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

	/* Filter */

	case 7: // KÖZELI HELYEK

	    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
		    "Közeli helyek keresése...", true, false);
	    new Thread(new Runnable() {

		@Override
		public void run() {
		    Session.getInstance().getSearchViewClubs().clear();
		    Session.getInstance()
			    .getSearchViewClubs()
			    .addAll(Session.getInstance().getActualCommunicationInterface()
				    .getClubsFromCityName(Session.getInstance().citynameFromGPS));
		    Session.getInstance().setPositions(activity);

		    activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
			    Session.getInstance().dismissProgressDialog();
			    ((ClubsUpdateableFragment) activity.fragments[0]).updateResults();
			    ((ClubsUpdateableFragment) activity.fragments[1]).updateResults();
			    ImageView ib = (ImageView) activity.findViewById(R.id.actionbar_clubs_button_a);
			    ib.setImageDrawable(activity.getResources().getDrawable(R.drawable.ab_filter_location));
			    ClubsActivity.sourceOfList = SourceOfList.LOCATION;
			}
		    });
		}
	    }).start();

	    break;

	case 2: // KEDVENCEK
	    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
		    "Kedvencek betöltése...", true, false);
	    new Thread(new Runnable() {

		@Override
		public void run() {
		    Session.getInstance().getSearchViewClubs().clear();
		    Session.getInstance().getSearchViewClubs()
			    .addAll(Session.getInstance().getActualUser().favoriteClubs);
		    Session.getInstance().setPositions(activity);

		    activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
			    Session.getInstance().dismissProgressDialog();
			    ((ClubsUpdateableFragment) activity.fragments[0]).updateResults();
			    ((ClubsUpdateableFragment) activity.fragments[1]).updateResults();
			    ImageView ib = (ImageView) activity.findViewById(R.id.actionbar_clubs_button_a);
			    ib.setImageDrawable(activity.getResources().getDrawable(R.drawable.ab_filter_favorites));
			    ClubsActivity.sourceOfList = SourceOfList.FAVORITES;
			}
		    });

		}
	    }).start();

	    break;

	case 3: // HELYEIM
	    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj", "Helyeid betöltése...",
		    true, false);
	    new Thread(new Runnable() {

		@Override
		public void run() {
		    Session.getInstance().getSearchViewClubs().clear();
		    Session.getInstance().getSearchViewClubs().addAll(Session.getInstance().getActualUser().usersClubs);
		    Session.getInstance().setPositions(activity);
		    activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
			    Session.getInstance().dismissProgressDialog();
			    ((ClubsUpdateableFragment) activity.fragments[0]).updateResults();
			    ((ClubsUpdateableFragment) activity.fragments[1]).updateResults();
			    ImageView ib = (ImageView) activity.findViewById(R.id.actionbar_clubs_button_a);
			    ib.setImageDrawable(activity.getResources().getDrawable(R.drawable.ab_filter_ownership));
			    ClubsActivity.sourceOfList = SourceOfList.OWNERSHIP;
			}
		    });

		}
	    }).start();

	    break;

	default:
	    Log.e("MAIN SCREEN", "POPUP MENU: NOT HANDLED onMenuItemClick");
	}

	return true;
    }

    @Override
    public void onServicesSetted(List<String> result) {
	numberOfSelectedServices = result.size();
	selectedServices = result;
	textViewSelectedServicesNumber.setText(numberOfSelectedServices + " szolgáltatás kiválasztva");
    }
}
