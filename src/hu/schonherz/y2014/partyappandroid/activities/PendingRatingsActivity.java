package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.PendingRatingListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.AdminRating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class PendingRatingsActivity extends ActionBarActivity {

    List<AdminRating> ratings;
    ListView ratingsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Jóváhagyandó értékelések").setLayout();

        setContentView(R.layout.activity_pending_ratings);

        ratings = PendingListActivity.ratings;

        ratingsListView = (ListView) findViewById(R.id.pending_rating_listview);
        registerForContextMenu(ratingsListView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        final int userId = ratings.get(index).userId;
        final int clubId = ratings.get(index).clubId;
        switch (item.getItemId()) {
        case R.id.accept_something:        
            Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                    "Elfogadás folyamatban...", true, false);

            new NetThread(this, new Runnable() {

                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().acceptRating(userId, clubId);
                    ratings.remove(index);

                    PendingRatingsActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingRatingsActivity.this, "Sikeres elfogadás!").show();
                            onResume();
                        }
                    });
                }
            }).start();
            
            return true;
        case R.id.decline_something:
            Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                    "Visszautasítás folyamatban...", true, false);

            new NetThread(this, new Runnable() {

                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().declineRating(userId, clubId);
                    ratings.remove(index);

                    PendingRatingsActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingRatingsActivity.this, "Sikeres visszautasítás!").show();
                            onResume();
                        }
                    });
                }
            }).start();
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pending_something_list_context_menu, menu);
    }

    @Override
    protected void onResume() {
        if (ratings == null) {

        } else
            ratingsListView.setAdapter(new PendingRatingListAdapter(this, getRatingArrayFromList(ratings)));
        super.onResume();
    }

    private AdminRating[] getRatingArrayFromList(List<AdminRating> ratingList) {
        AdminRating[] ratingArray = new AdminRating[ratingList.size()];
        for (int i = 0; i < ratingList.size(); ++i) {
            ratingArray[i] = ratingList.get(i);
        }
        return ratingArray;
    }
        
    @Override
    public void onBackPressed() {
        PendingListActivity.updateButtonText();
        super.onBackPressed();
    }
}
