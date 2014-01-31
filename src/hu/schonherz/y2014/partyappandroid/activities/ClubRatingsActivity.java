package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.RatingsActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.RatingListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class ClubRatingsActivity extends ActionBarActivity {

    List<Rating> ratingsList;
    ListView ratingsListView;
    ImageView addButton;
    int clubListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new RatingsActionBar(this).setLayout();

        setContentView(R.layout.activity_club_ratings);

        clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
        ratingsList = Session.getSearchViewClubs().get(clubListPosition).ratings;

        ratingsListView = (ListView) findViewById(R.id.club_ratings_list);

        addButton = (ImageView) findViewById(R.id.actionbar_ratings_button_a);
        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ClubRatingAdd.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        // addButton.setVisibility(View.VISIBLE);
        if (ratingsList == null) {

        } else {
            ratingsList = Session.getSearchViewClubs().get(clubListPosition).ratings;
            ratingsListView.setAdapter(new RatingListAdapter(this, getRatingArrayFromList(ratingsList)));
        }
        super.onResume();
    }

    private Rating[] getRatingArrayFromList(List<Rating> ratingList) {
        Rating[] ratingArray = new Rating[ratingList.size()];
        for (int i = 0; i < ratingList.size(); ++i) {
            ratingArray[i] = ratingList.get(i);
        }
        return ratingArray;
    }

}
