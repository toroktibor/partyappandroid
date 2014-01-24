package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.OwnerRequestListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PendingOwnerRequest extends ActionBarActivity {
	
	List<OwnerRequest> ownerRequestList;
	ListView ownerRequestListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_pending_owner_request);
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.pending_list_actionbar);
		
		TextView menuText = (TextView) findViewById(R.id.pending_list_actionbar_name);
		menuText.setText("Tulajdonosi kérelem jóváhagyások");
		
		ownerRequestList = Session.getInstance().getActualCommunicationInterface().getNotApprovedOwnerRequest();
		
		ownerRequestListView = (ListView) findViewById(R.id.pending_owner_reguest_listview);
		registerForContextMenu(ownerRequestListView);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int index = info.position;
		int club_id = ownerRequestList.get(index).getClub().id;
		int user_id = ownerRequestList.get(index).getUser().getId();
    	switch(item.getItemId()) {
		case R.id.accept_something:
			Session.getInstance().getActualCommunicationInterface().acceptOwnerRequest(club_id, user_id);
			ownerRequestList.remove(index);
			onResume();
			return true;
		case R.id.decline_something:
			Session.getInstance().getActualCommunicationInterface().declineOwnerRequest(club_id, user_id);
			ownerRequestList.remove(index);
			onResume();
			return true;
		default:
			return super.onContextItemSelected(item);
    	}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pending_something_list_context_menu, menu);
	}
	
	@Override
	protected void onResume() {
		ownerRequestListView.setAdapter(new OwnerRequestListAdapter(this, getOwnerRequestArrayFromList(ownerRequestList)));
		super.onResume();
	}
	
	private OwnerRequest[] getOwnerRequestArrayFromList(List<OwnerRequest> ownerRequestList){
		OwnerRequest[] ownerRequestArray = new OwnerRequest[ownerRequestList.size()];
    	for(int i = 0; i < ownerRequestList.size(); ++i){
    		ownerRequestArray[i] = ownerRequestList.get(i);
    	}
    	return ownerRequestArray;
    }

}
