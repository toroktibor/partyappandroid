package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.ClubListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class PendingClubsListActivity extends ActionBarActivity {

    List<Club> newClubsList;
    ListView newClubsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Jóváhagyandó klubok").setLayout();

        setContentView(R.layout.activity_pending_clubs_list);

        newClubsList = PendingListActivity.newClubsList;

        newClubsListView = (ListView) findViewById(R.id.pending_clubs_listview);
        registerForContextMenu(newClubsListView);
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        final int club_id = newClubsList.get(index).id;
        switch (item.getItemId()) {
        case R.id.accept_something:
            
            Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                    "Elfogadás folyamatban...", true, false);

            new NetThread(this, new Runnable() {

                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().approveClub(club_id);
                    newClubsList.remove(index);

                    PendingClubsListActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingClubsListActivity.this, "Sikeres elfogadás!").show();
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
                    Session.getInstance().getActualCommunicationInterface().declineNewClub(club_id);
                    newClubsList.remove(index);

                    PendingClubsListActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingClubsListActivity.this, "Sikeres visszautasítás!").show();
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
        if (newClubsList == null) {

        } else
            newClubsListView.setAdapter(new ClubListAdapter(this, getClubArrayFromList(newClubsList)));
        super.onResume();
    }

    private Club[] getClubArrayFromList(List<Club> clubList) {
        Club[] clubArray = new Club[clubList.size()];
        for (int i = 0; i < clubList.size(); ++i) {
            clubArray[i] = clubList.get(i);
        }
        return clubArray;
    }

}
