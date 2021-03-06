package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClubListAdapter extends ArrayAdapter<Club> {

    public ClubListAdapter(Context context, Club[] objects) {
        super(context, R.layout.club_list_item, objects);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            item = inflater.inflate(R.layout.club_list_item, parent, false);
        }

        ((TextView) item.findViewById(R.id.club_list_item_name)).setText(((Club) getItem(position)).name);
        ((TextView) item.findViewById(R.id.club_list_item_address)).setText(((Club) getItem(position)).address);
        item.setTag((Club) getItem(position));

        if (((Club) getItem(position)).highlite_expire != null) {
            if (!((Club) getItem(position)).highlite_expire.equals("null")) {
                Log.i("próba log", "itt van sárgítás");
                Log.i("itt", ((Club) getItem(position)).highlite_expire + " " + ((Club) getItem(position)).name + " "
                        + position);
                item.setBackgroundResource(R.drawable.high_club_item1);
                item.setPadding(15, 7, 7, 7);
            } else {
                item.setBackgroundColor(getContext().getResources().getColor(R.color.applicationWindowBackground));
                item.setPadding(7, 7, 7, 7);
            }
        }

        return item;
    }

}
