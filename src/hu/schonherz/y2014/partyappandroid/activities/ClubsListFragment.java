package hu.schonherz.y2014.partyappandroid.activities;

import java.util.List;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.ClubListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ClubsListFragment extends Fragment {	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_clubs_list, container, false);
    	
    	
    	ListView clubsListView = new ListView(getActivity().getApplicationContext());
    	Log.d("FELSOROLÁS ELEJE","HURRÁ BÉBI! :D");
    	Club[] clubArray = getClubArrayFromClubsList(Session.getSearchViewClubs());
    	//Log.i("jojo",clubArray[0].address);
    	clubsListView.setAdapter(new ClubListAdapter(getActivity(), clubArray));
    	rootView.addView(clubsListView);
    	return rootView;
    }

    private Club[] getClubArrayFromClubsList(List<Club> clubList) {
    	Club[] clubArray = new Club[clubList.size()];
    	for(int i = 0; i < clubList.size(); ++i) {
    		clubArray[i] = clubList.get(i);
    	}
    	return clubArray;
    }
    
    
}
