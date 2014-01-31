package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.adapters.OwnerRequestListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
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

public class PendingOwnerRequest extends ActionBarActivity {

    List<OwnerRequest> ownerRequestList;
    ListView ownerRequestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Jóváhagyandó tulajdonosok").setLayout();

        setContentView(R.layout.activity_pending_owner_request);

        ownerRequestList = PendingListActivity.ownerRequestList;

        ownerRequestListView = (ListView) findViewById(R.id.pending_owner_reguest_listview);
        registerForContextMenu(ownerRequestListView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        final int club_id = ownerRequestList.get(index).getClub().id;
        final int user_id = ownerRequestList.get(index).getUser().getId();
        switch (item.getItemId()) {
        case R.id.accept_something:
            
            Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                    "Elfogadás folyamatban...", true, false);

            new NetThread(this, new Runnable() {

                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().acceptOwnerRequest(club_id, user_id);
                    ownerRequestList.remove(index);

                    PendingOwnerRequest.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingOwnerRequest.this, "Sikeres elfogadás!").show();
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
                    Session.getInstance().getActualCommunicationInterface().declineOwnerRequest(club_id, user_id);
                    ownerRequestList.remove(index);

                    PendingOwnerRequest.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(PendingOwnerRequest.this, "Sikeres visszautasítás!").show();
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
        if (ownerRequestList == null) {

        } else {
            ownerRequestListView.setAdapter(new OwnerRequestListAdapter(this,
                    getOwnerRequestArrayFromList(ownerRequestList)));
        }
        super.onResume();
    }

    private OwnerRequest[] getOwnerRequestArrayFromList(List<OwnerRequest> ownerRequestList) {
        OwnerRequest[] ownerRequestArray = new OwnerRequest[ownerRequestList.size()];
        for (int i = 0; i < ownerRequestList.size(); ++i) {
            ownerRequestArray[i] = ownerRequestList.get(i);
        }
        return ownerRequestArray;
    }
    
    @Override
    public void onBackPressed() {
        PendingListActivity.updateButtonText();
        super.onBackPressed();
    }
}
