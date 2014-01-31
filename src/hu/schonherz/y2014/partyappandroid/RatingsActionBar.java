package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubRatingsActivity;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

public class RatingsActionBar {

    private final ClubRatingsActivity activity;

    public RatingsActionBar(ClubRatingsActivity activity) {
        this.activity = activity;
    }

    public void setLayout() {
        ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_ratings, null);
        ActionBar ab = activity.getSupportActionBar();

        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setCustomView(actionBarLayout);

    }

}
