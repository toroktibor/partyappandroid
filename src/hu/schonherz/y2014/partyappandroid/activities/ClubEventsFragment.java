package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClubEventsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_club_events, container, false);
    	return rootView;
    }

}