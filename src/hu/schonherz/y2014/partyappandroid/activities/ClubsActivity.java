package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubsActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

public class ClubsActivity extends ActionBarActivity {

    public ViewPager viewPager;
    public ClubsUpdateableFragment[] fragments = new ClubsUpdateableFragment[2];
    private Toast backToast;
    
    public enum SourceOfList {
	LOCATION, SEARCH, OWNERSHIP, FAVORITES
    };
    
    public enum SourceOfView {
    	LIST, MAP
    };

    public static SourceOfList sourceOfList = SourceOfList.LOCATION;
    public static SourceOfView sourceOfView = SourceOfView.LIST;

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	public ScreenSlidePagerAdapter(FragmentManager fm) {
	    super(fm);
	}

	@Override
	public Fragment getItem(int position) {
	    if (position == 0) {
		return new ClubsListFragment();
	    }
	    return new ClubsMapFragment();
	}

	@Override
	public int getCount() {
	    return 2;
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	Log.i("asdasd","clubs onCreate");
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_clubs);

	ClubsActionBar ab = new ClubsActionBar(this);
	ab.setLayout();
	

	viewPager = (ViewPager) findViewById(R.id.clubs_viewpager);
	ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	viewPager.setAdapter(mPagerAdapter);

    }


    @Override
    public void onBackPressed() {
        
	
        if ( backToast == null || backToast.getView().getWindowToken() == null ){
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    	    vibrator.vibrate(new long[] { 0, 50 }, -1); // egy rövid
            backToast = Toast.makeText(this, "A kilépéshez nyomd meg a vissza gombot!", Toast.LENGTH_SHORT);
            backToast.show();
        }else{
            super.onBackPressed();
        }
    }
    
}
