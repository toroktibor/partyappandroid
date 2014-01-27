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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ClubEventsFragment extends Fragment {

    ListView eventsListView;
    Button addButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_events, container, false);

	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	eventsListView = new ListView(getActivity().getApplicationContext());
	Event[] eventArray = getEventArrayFromList(Session.getSearchViewClubs().get(clubListPosition).events);
	eventsListView.setAdapter(new EventsListAdapter(getActivity(), eventArray));
	addButton = (Button) rootView.findViewById(R.id.club_events_add_button);

	eventsListView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Activity activity = getActivity();
		Intent i = new Intent(activity, ClubEventDetailsActivity.class);
		i.putExtra("listPosition", arg2);
		activity.startActivity(i);
	    }

	});

	addButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		Activity activity = getActivity();
		Intent i = new Intent(activity, ClubEventNewActivity.class);
		activity.startActivity(i);

	    }
	});

	rootView.addView(eventsListView);

	// tulajextra funkcioi
	if (ClubActivity.isClubOfActualUser) {
	    registerForContextMenu(eventsListView);
	} else {
	    addButton.setVisibility(View.INVISIBLE);
	}

	return rootView;
    }

    private Event[] getEventArrayFromList(List<Event> eventList) {
	Event[] eventArray = new Event[eventList.size()];
	for (int i = 0; i < eventList.size(); ++i) {
	    eventArray[i] = eventList.get(i);
	}
	return eventArray;
    }

    @Override
    public void onResume() {
    	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
    	Event[] eventArray = getEventArrayFromList(Session.getSearchViewClubs().get(clubListPosition).events);
    	eventsListView.setAdapter(new EventsListAdapter(getActivity(), eventArray));
	super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
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
	switch (item.getItemId()) {
	case R.id.delete_event_menu_item:
	    int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	    int eventId = Session.getSearchViewClubs().get(clubListPosition).events.get(index).id;
	    Session.getSearchViewClubs().get(clubListPosition).events.remove(index);
	    Session.getInstance().getActualCommunicationInterface().deleteEvent(eventId);
	    onResume();
	    return true;
	case R.id.modify_event_menu_item:
	    Activity activity = getActivity();
	    Intent i = new Intent(activity, ClubEventModifyActivity.class);
	    i.putExtra("eventsListPosition", index);
	    activity.startActivity(i);
	    return true;
	default:
	    return super.onContextItemSelected(item);
	}
    }

}
