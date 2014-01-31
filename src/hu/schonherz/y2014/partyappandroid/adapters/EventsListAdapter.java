package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventsListAdapter extends ArrayAdapter {
    public EventsListAdapter(Context context, Event[] objects) {
        super(context, R.layout.event_list_item, objects);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            item = inflater.inflate(R.layout.event_list_item, parent, false);
        }
        ((TextView) item.findViewById(R.id.event_list_item_name)).setText(((Event) getItem(position)).name);
        ((TextView) item.findViewById(R.id.event_list_item_date)).setText(((Event) getItem(position)).start_date);
        ((TextView) item.findViewById(R.id.event_list_item_music_type)).setText(((Event) getItem(position)).music_type);
        item.setTag((Event) getItem(position));
        return item;
    }
}
