package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class ClubActivity extends ActionBarActivity {

    public ViewPager viewPager;
    static public Intent intent;

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

	ClubActionBar ab = new ClubActionBar(this);
	ab.setLayout();

	viewPager = (ViewPager) findViewById(R.id.club_viewpager);
	ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	viewPager.setAdapter(mPagerAdapter);

    }

}
