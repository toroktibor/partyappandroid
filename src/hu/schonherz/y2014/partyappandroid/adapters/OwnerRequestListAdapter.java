package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OwnerRequestListAdapter extends ArrayAdapter<OwnerRequest> {
    public OwnerRequestListAdapter(Context context, OwnerRequest[] objects) {
	super(context, R.layout.owner_request_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View item = convertView;
	if (item == null) {
	    LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
	    item = inflater.inflate(R.layout.owner_request_list_item, parent, false);
	}

	OwnerRequest actualOwnerRequest = (OwnerRequest) getItem(position);
	((TextView) item.findViewById(R.id.peding_owner_request_club_name_textview)).setText(actualOwnerRequest
		.getClub().name);
	((TextView) item.findViewById(R.id.peding_owner_request_club_address_textview)).setText(actualOwnerRequest
		.getClub().address);
	((TextView) item.findViewById(R.id.peding_owner_request_user_name_textview)).setText(actualOwnerRequest
		.getUser().getNickname());
	((TextView) item.findViewById(R.id.peding_owner_request_user_email_textview)).setText(actualOwnerRequest
		.getUser().getEmail());

	item.setTag((OwnerRequest) getItem(position));
	return item;
    }
}
