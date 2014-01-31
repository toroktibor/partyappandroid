package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

public class MenuActionBar {

    private final ClubMenuActivity activity;

    public MenuActionBar(ClubMenuActivity activity) {
        this.activity = activity;
    }

    public void setLayout() {
        ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_menu, null);
        ActionBar ab = activity.getSupportActionBar();

        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setCustomView(actionBarLayout);

    }

}
