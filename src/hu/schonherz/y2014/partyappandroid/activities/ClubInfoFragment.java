package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ClubInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_info, container, false);

	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	Log.i("átjött", (new Integer(clubListPosition)).toString());
	clubFullDownload(clubListPosition);
	Club actualClub = Session.getSearchViewClubs().get(clubListPosition);
	
	
	
	TextView clubNameTextView = (TextView) rootView.findViewById(R.id.club_info_textview_name);
	TextView clubAddressTextView = (TextView) rootView.findViewById(R.id.club_info_textview_address);
	TextView clubDescriptionTextView = (TextView) rootView.findViewById(R.id.club_info_textview_description);
	
	clubNameTextView.setText(actualClub.name);
	clubAddressTextView.setText(actualClub.address);
	clubDescriptionTextView.setText(actualClub.description);
	
	return rootView;
    }

    public void onClickHandler(View v) {
	switch (v.getId()) {
	case R.id.club_info_ratingbar:
	    startActivity(new Intent(getActivity(), ClubReviewsActivity.class));
	}
    }

    protected void clubFullDownload(int actualClubPosition){
    	Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
    	if (actualCLub.isNotFullDownloaded()){
    		Session.getSearchViewClubs().set(actualClubPosition, Session.getInstance().getActualCommunicationInterface().getEverythingFromClub(actualCLub.id));
    	}
    };
    
}
