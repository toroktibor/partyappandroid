package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

public class ClubActivity extends ActionBarActivity {

	public ViewPager viewPager;
	static public Intent intent;
	static public boolean isClubOfActualUser;

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new ClubInfoFragment();
			case 1:
				return new ClubEventsFragment();
			case 2:
				return new ClubGaleryFragment();
			}
			return null;

		}

		@Override
		public int getCount() {
			return 3;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club);
		intent = getIntent();
		int clubListPosition = ClubActivity.intent.getExtras().getInt(
				"listPosition");
		isClubOfActualUser = Session.getActualUser().isMine(
				Session.getSearchViewClubs().get(clubListPosition).id);

		ClubActionBar ab = new ClubActionBar(this);
		ab.setLayout();

		viewPager = (ViewPager) findViewById(R.id.club_viewpager);
		ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(
				getSupportFragmentManager());
		viewPager.setAdapter(mPagerAdapter);

	}

	public void phoneCall(View v) {

		int clubListPosition = ClubActivity.intent.getExtras().getInt(
				"listPosition");
		clubFullDownload(clubListPosition);
		Club actualClub = Session.getSearchViewClubs().get(clubListPosition);
		String num = actualClub.phonenumber;
		String number = "tel:" + num;

		Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse(number));
		startActivity(callIntent);
	}
	
	public void message(View v){
		Intent messageIntent = new Intent(getApplicationContext(),VisitorEmail.class);
		startActivity(messageIntent);
	}

	protected void clubFullDownload(int actualClubPosition) {
		Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
		if (actualCLub.isNotFullDownloaded()) {
			Session.getSearchViewClubs().set(
					actualClubPosition,
					Session.getInstance

					().getActualCommunicationInterface()
							.getEverythingFromClub(actualCLub.id));
		}
	};

}
