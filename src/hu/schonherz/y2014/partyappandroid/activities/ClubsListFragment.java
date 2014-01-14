package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    	
    	
    	// ListView clubsListView = new ListView(getActivity().getApplicationContext());
    	// rootView.addView(clubsListView);
    	return rootView;
    }

}
