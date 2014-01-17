package hu.schonherz.y2014.partyappandroid.activities;

import java.util.List;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.EventsListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ClubEventsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_events, container, false);
	
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	ListView eventsListView = new ListView(getActivity().getApplicationContext());
	Event[] eventArray = getEventArrayFromList(Session.getSearchViewClubs().get(clubListPosition).events);
	eventsListView.setAdapter(new EventsListAdapter(getActivity(), eventArray));
	
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
