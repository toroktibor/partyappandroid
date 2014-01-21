package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.EventsListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class ClubEventsFragment extends Fragment {
	
	ListView eventsListView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_events, container, false);
	
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	eventsListView = new ListView(getActivity().getApplicationContext());
	Event[] eventArray = getEventArrayFromList(Session.getSearchViewClubs().get(clubListPosition).events);
	eventsListView.setAdapter(new EventsListAdapter(getActivity(), eventArray));
	
	eventsListView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Activity activity = getActivity();
		Intent i = new Intent(activity, ClubEventDetailsActivity.class);
		i.putExtra("listPosition", arg2);
		activity.startActivity(i);
	    }

	});
	
	rootView.addView(eventsListView);
	
	//tulajextra funkcioi
	if(ClubActivity.isClubOfActualUser){
		registerForContextMenu(eventsListView);
	}
	
	return rootView;
    }
    
    private Event[] getEventArrayFromList(List<Event> eventList){
    	Event[] eventArray = new Event[eventList.size()];
    	for(int i = 0; i< eventList.size(); ++i) {
    		eventArray[i]=eventList.get(i);
    	}
    	return eventArray;
    }
    
    @Override
    public void onResume() {    	
    	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
    	Event[] eventArray = getEventArrayFromList(Session.getSearchViewClubs().get(clubListPosition).events);
    	eventsListView.setAdapter(new EventsListAdapter(getActivity(), eventArray));
    	
    	eventsListView.setOnItemClickListener(new OnItemClickListener() {

    	    @Override
    	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    		Activity activity = getActivity();
    		Intent i = new Intent(activity, ClubEventDetailsActivity.class);
    		i.putExtra("listPosition", arg2);
    		activity.startActivity(i);
    	    }

    	});
    	
    	
    	//tulajextra funkcioi
    	if(ClubActivity.isClubOfActualUser){
    		registerForContextMenu(eventsListView);
    	}
    	
 
    	super.onResume();
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.club_events_context_menu, menu);			
	}
    
    @Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int index = info.position;
		switch(item.getItemId()) {
		case R.id.delete_event_menu_item:
			//kuldunk majd egy delete uzcsit
			int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
			Session.getSearchViewClubs().get(clubListPosition).events.remove(index);
			onResume();
			return true;
		case R.id.modify_event_menu_item:					    
			Activity activity = getActivity();
			Intent i = new Intent(activity,ClubEventModifyActivity.class);
			i.putExtra("eventsListPosition", index);
			activity.startActivity(i);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
    
}
