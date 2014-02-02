package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ClubsActionBar;
import hu.schonherz.y2014.partyappandroid.InfoToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
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
    protected void onRestart() {
        ((ClubsListFragment) fragments[0]).updateResults();
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("asdasd", "clubs onCreate");

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
        if (InfoToast.t == null || InfoToast.t.getView().getWindowToken() == null) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[] { 0, 50 }, -1); // egy rövid
            new InfoToast(this,"A kilépéshez nyomd meg a vissza gombot!").show();
        } else {
            if (InfoToast.t != null ){
                InfoToast.t.cancel();
            }
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (Session.getInstance().oldPhone) {
            ImageView ib = (ImageView) findViewById(R.id.actionbar_clubs_button_a);
            if (sourceOfList.equals(SourceOfList.FAVORITES)) {
                ib.setImageDrawable(getResources().getDrawable(R.drawable.ab_filter_favorites));
            } else if (sourceOfList.equals(SourceOfList.LOCATION)) {
                ib.setImageDrawable(getResources().getDrawable(R.drawable.ab_filter_location));
            } else if (sourceOfList.equals(SourceOfList.OWNERSHIP)) {
                ib.setImageDrawable(getResources().getDrawable(R.drawable.ab_filter_ownership));
            } else if (sourceOfList.equals(SourceOfList.SEARCH)) {
                ib.setImageDrawable(getResources().getDrawable(R.drawable.ab_filter_search));
            }
            if (sourceOfView.equals(SourceOfView.LIST)) {
                viewPager.setCurrentItem(0);
                ((ImageView) findViewById(R.id.actionbar_clubs_button_b)).setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.ab_selected));
                ((ImageView) findViewById(R.id.actionbar_clubs_button_c)).setBackgroundDrawable(null);
            } else {
                viewPager.setCurrentItem(1);
                ((ImageView) findViewById(R.id.actionbar_clubs_button_c)).setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.ab_selected));
                ((ImageView) findViewById(R.id.actionbar_clubs_button_b)).setBackgroundDrawable(null);
            }
        }
        super.onResume();
    }
}
