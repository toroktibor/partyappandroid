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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ClubEventsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_events, container, false);
	
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	ListView eventsListView = new ListView(getActivity().getApplicationContext());
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
	return rootView;
    }
    
    private Event[] getEventArrayFromList(List<Event> eventList){
    	Event[] eventArray = new Event[eventList.size()];
    	for(int i = 0; i< eventList.size(); ++i) {
    		eventArray[i]=eventList.get(i);
    	}
    	return eventArray;
    }
    
}
