package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.ClubListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ClubsListFragment extends Fragment implements ClubsUpdateableFragment {

    private ListView clubsListView;

    
    public enum SourceOfList {
	LOCATION, SEARCH, OWNERSHIP, FAVORITES
    };

    /* A szűrés módját tárolja. Ez alapján generálhatjuk a hibaüzenetet, ha nincs találat, és tanácsot is adhatunk neki.
     * Pl.: A keresés nem adott eredményt, próbálj meg kevesebb feltételt megadni.
     */
    public SourceOfList sourceOfList = SourceOfList.LOCATION;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clubs_list, container, false);

	clubsListView = new ListView(getActivity().getApplicationContext());
	Log.d("FELSOROLÁS ELEJE", "HURRÁ BÉBI! :D");
	Club[] clubArray = getClubArrayFromClubsList(Session.getSearchViewClubs());
	// Log.i("jojo",clubArray[0].address);
	clubsListView.setAdapter(new ClubListAdapter(getActivity(), clubArray));
	// clubsListView.setClickable(true);
	clubsListView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.d("ONCLICKLISTENER FUT", (new Integer(arg2).toString()));
		// Intent intent = new Intent(getActivity(),
		// ClubActivity.class);
		// intent.putExtra("listPosition", arg2);
		// activity.startActivity(intent);
		Activity activity = getActivity();
		Intent i = new Intent(activity, ClubActivity.class);
		i.putExtra("listPosition", arg2);
		activity.startActivity(i);
	    }

	});
	rootView.addView(clubsListView);
	return rootView;
    }

    private Club[] getClubArrayFromClubsList(List<Club> clubList) {
	Club[] clubArray = new Club[clubList.size()];
	for (int i = 0; i < clubList.size(); ++i) {
	    clubArray[i] = clubList.get(i);
	}
	return clubArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	Log.i("asdasd", "onActivityResult");
	super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);
	((ClubsActivity) activity).currentFragment = this;
    }

    @Override
    public void updateResults() {
	clubsListView.setAdapter(new ClubListAdapter(getActivity(), getClubArrayFromClubsList(Session
		.getSearchViewClubs())));

	ListView lv = (ListView) getView().findViewById(R.id.clubs_list_listview);
	// ClubListAdapter cla = (ClubListAdapter) lv.getAdapter();
	lv.invalidateViews();

	TextView tv = (TextView) getActivity().findViewById(R.id.clubs_list_textview_message);
	
	
	
	if (Session.getSearchViewClubs().size() == 0 ) {
	    tv.setText("Sajnos nincs találat :(");
	    tv.setVisibility(View.VISIBLE);
	} else {
	    tv.setVisibility(View.GONE);

	}

    }

}
