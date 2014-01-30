package hu.schonherz.y2014.partyappandroid.activities;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ClubInfoFragment extends Fragment {

	private Club actualClub;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_club_info, container, false);

		int clubListPosition = ClubActivity.intent.getExtras().getInt(
				"listPosition");
		//Log.i("átjött", String.valueOf(clubListPosition));
		actualClub = Session.getSearchViewClubs().get(clubListPosition);

		if (actualClub.isNotFullDownloaded()) {
			actualClub.downloadEverything();
		}

		RatingBar clubRatingBar = (RatingBar) rootView
				.findViewById(R.id.club_info_ratingbar);
		TextView clubNameTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_name);
		TextView clubAddressTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_address);
		TextView clubDescriptionTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_description);
		TextView clubServicesTextView = (TextView) rootView
				.findViewById(R.id.textViewServices);
		ImageButton call = (ImageButton) rootView.findViewById(R.id.phone_call);
		ImageButton message = (ImageButton) rootView.findViewById(R.id.message);
		ImageButton showOnTheMap = (ImageButton) rootView.findViewById(R.id.showOnTheMap);
		

		if (actualClub.phonenumber == null
				|| actualClub.phonenumber.equals("null")) {
		 // TODO:ikon csere
			call.setEnabled(false);
			call.setBackgroundColor(Color.TRANSPARENT);
		} else {
		 // TODO:ikon csere
			call.setEnabled(true);
		}

		if (actualClub.email == null || actualClub.email.equals("null")) {
			// TODO:ikon csere
			message.setEnabled(false);
			call.setBackgroundColor(Color.TRANSPARENT);
		} else {
			message.setEnabled(true);
			// TODO:ikon csere
		}

		if(actualClub.position == null || actualClub.position.equals("null")) {
		    	// TODO: ikon csere
		    showOnTheMap.setEnabled(false);
		    showOnTheMap.setBackgroundColor(Color.TRANSPARENT);
		} else {
		    	// TODO: ikon csere
		    showOnTheMap.setEnabled(true);
		}
		
		
		clubNameTextView.setText(actualClub.name);
		clubAddressTextView.setText(actualClub.address);
		clubDescriptionTextView.setText(actualClub.description);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Szolgáltatások");
		String actServ = new String();
		
		Log.e("INFO", "NO. OF SERVICES" + actualClub.services.size() );
		for (int i = 0; i < actualClub.services.size(); ++i) {
		    actServ = actualClub.services.get(i);
		    Log.e("INFO", actServ);
		    sb.append(Session.getInstance().servicesNameList.get(
			    		Session.getInstance().servicesTokenList.indexOf(actServ.toString())).toString());
		    if(i != actualClub.services.size()-1)
			sb.append(", ");
		}
		String serviceList = sb.toString();
		Log.e("INFO", "SERVICES: " + serviceList);
		clubServicesTextView.setText(serviceList);
		Log.e("INFO", "SERVICES SETTED");
		
		clubRatingBar.setRating(actualClub.getAvarageRate());

		clubRatingBar.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent i = new Intent(getActivity(), ClubRatingsActivity.class);
				startActivity(i);
				return false;
			}
		});

		return rootView;
	}

	public void onClickHandler(View v) {
		switch (v.getId()) {
		case R.id.club_info_ratingbar:
			startActivity(new Intent(getActivity(), ClubReviewsActivity.class));
		}
	}

}
