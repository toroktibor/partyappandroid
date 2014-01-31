package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.AdminRating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PendingListActivity extends ActionBarActivity {

    static List<AdminRating> ratings;
    static List<OwnerRequest> ownerRequestList;
    static ArrayList<GaleryImage> images = new ArrayList<GaleryImage>();
    static List<Club> newClubsList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Jóváhagyandó tételek").setLayout();

        setContentView(R.layout.activity_pending_list);
        
        final Button pendingClubsButton = (Button) findViewById(R.id.pendings_new_clubs_button);
        final Button pendingRatingsButton = (Button) findViewById(R.id.pendings_new_rating_button);
        final Button pendingImagesButton = (Button) findViewById(R.id.pendings_new_image_button);
        final Button pendingOwnersButton = (Button) findViewById(R.id.pendings_owner_please_button);

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
        
        Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                "Letöltés folyamatban...", true, false);

        new NetThread(this, new Runnable() {

            @Override
            public void run() {
                ratings = Session.getInstance().getActualCommunicationInterface().getNotApprovedRatings();
                ownerRequestList = Session.getInstance().getActualCommunicationInterface().getNotApprovedOwnerRequest();
                images.clear();
                ArrayList<Integer> imageIDList = (ArrayList<Integer>) Session.getInstance().getActualCommunicationInterface()
                        .getNotApprovedImages();
                Log.i("asd", "" + imageIDList.size());
                for (int i = 0; i < imageIDList.size(); i++) {
                    images.add(new GaleryImage(imageIDList.get(i), (ImageUtils.StringToBitMap(Session.getInstance()
                            .getActualCommunicationInterface().DownLoadAnImageThumbnail(imageIDList.get(i))))));
                }
                newClubsList = Session.getInstance().getActualCommunicationInterface().getNotApprovedClubs();
                
                PendingListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Session.getInstance().dismissProgressDialog();
                        pendingClubsButton.setText("Szórakozóhelyek ("+newClubsList.size()+")");
                        pendingOwnersButton.setText("Tulajdonosi kérelmek ("+ownerRequestList.size()+")");
                        pendingImagesButton.setText("Képek ("+images.size()+")");
                        pendingRatingsButton.setText("Vélemények ("+ratings.size()+")");
                    }
                });
            }
        }).start();
    }

}
