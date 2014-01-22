package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClubsMapFragment extends Fragment implements ClubsUpdateableFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clubs_map, container, false);
	return rootView;
    }

    @Override
    public void updateResults() {
	Log.i(this.getClass().getName(),"Térkép találatok frissítése");
	
    }

}
