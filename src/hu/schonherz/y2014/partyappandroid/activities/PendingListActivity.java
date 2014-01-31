package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PendingListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Jóváhagyandó tételek").setLayout();

        setContentView(R.layout.activity_pending_list);

        Button pendingClubsButton = (Button) findViewById(R.id.pendings_new_clubs_button);
        Button pendingRatingsButton = (Button) findViewById(R.id.pendings_new_rating_button);
        Button pendingImagesButton = (Button) findViewById(R.id.pendings_new_image_button);
        Button pendingOwnersButton = (Button) findViewById(R.id.pendings_owner_please_button);

        pendingClubsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PendingClubsListActivity.class);
                startActivity(i);
            }
        });

        pendingOwnersButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PendingOwnerRequest.class);
                startActivity(i);
            }
        });

        pendingRatingsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PendingRatingsActivity.class);
                startActivity(i);
            }
        });

        pendingImagesButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PendingImageActivity.class);
                startActivity(i);
            }
        });
    }

}
