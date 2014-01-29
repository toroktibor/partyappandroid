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
	

	Integer[] icons = { R.drawable.club_service_icon_64px_billiard,
						R.drawable.club_service_icon_64px_bowling,
						R.drawable.club_service_icon_64px_coctailbar,
						R.drawable.club_service_icon_64px_dance,
						R.drawable.club_service_icon_64px_darts,
						R.drawable.club_service_icon_64px_dj,
						R.drawable.club_service_icon_64px_fndcontrol,
						R.drawable.club_service_icon_64px_livemusic,
						R.drawable.club_service_icon_64px_menu,
						R.drawable.club_service_icon_64px_sporttv,
						R.drawable.club_service_icon_64px_wifi };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_club_info, container, false);
		/*if (actualClub.services.size() > 0) {
			Log.e("CLUB INFO", "PICTURES WILL BE ADDED NOW");
			LinearLayout servicesLayout = (LinearLayout) rootView.findViewById(R.id.club_info_fragment_linear_layout_for_services);
			for (int i = 0; i < services.length; ++i) {
				String actualService = services[i];
				if (actualClub.services.contains(actualService)) {
					
					ImageView serviceIcon = new ImageView(getActivity());
					Log.e("CLUB INFO","IMAGEVIEW INSTANTIATED");
					serviceIcon.setImageResource(icons[i]);
					Log.e("CLUB INFO", "RESOURSE FOUNDED");
					servicesLayout.addView(serviceIcon, 25, 25);
					Log.e("CLUB INFO", "PICTURE ADDED");
				}
			}
		}
		*/
		int clubListPosition = ClubActivity.intent.getExtras().getInt(
				"listPosition");
		Log.i("átjött", String.valueOf(clubListPosition));
		actualClub = Session.getSearchViewClubs().get(clubListPosition);

		if (actualClub.isNotFullDownloaded()) {
			actualClub.downloadEverything();
		}

		Club actualClub = Session.getSearchViewClubs().get(clubListPosition);

		RatingBar clubRatingBar = (RatingBar) rootView
				.findViewById(R.id.club_info_ratingbar);
		TextView clubNameTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_name);
		TextView clubAddressTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_address);
		TextView clubDescriptionTextView = (TextView) rootView
				.findViewById(R.id.club_info_textview_description);
		LinearLayout servicesLayout = (LinearLayout) rootView.findViewById(R.id.club_info_fragment_linear_layout_for_services);
		ImageButton call = (ImageButton) rootView.findViewById(R.id.phone_call);
		ImageButton message = (ImageButton) rootView.findViewById(R.id.message);

		if (actualClub.phonenumber == null
				|| actualClub.phonenumber.equals("null")) {
			call.setEnabled(false);
			call.setBackgroundColor(Color.TRANSPARENT);
		} else {
			call.setEnabled(true);
		}

		if (actualClub.email == null || actualClub.email.equals("null")) {
			// TODO:ikon csere
			message.setEnabled(false);
		} else {
			message.setEnabled(true);
			// TODO:ikon csere
		}

		clubNameTextView.setText(actualClub.name);
		clubAddressTextView.setText(actualClub.address);
		clubDescriptionTextView.setText(actualClub.description);
		for (String service : actualClub.services) {
			if(Session.getInstance().servicesTokenList.contains(service)) {
				TextView tw = new TextView(getActivity());
				tw.setText(Session.getInstance().servicesNameList.get(Session.getInstance().servicesTokenList.indexOf(service)));
				servicesLayout.addView(tw);
			}
		}
		
		
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
