package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.AdminRating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class PendingRatingListAdapter extends ArrayAdapter<AdminRating> {
    public PendingRatingListAdapter(Context context, AdminRating[] objects) {
	super(context, R.layout.pending_rating_list_item, objects);
	// TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View item = convertView;
	if (item == null) {
	    LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
	    item = inflater.inflate(R.layout.pending_rating_list_item, parent, false);
	}

	AdminRating actualRating = (AdminRating) getItem(position);

	TextView ratingerUserName = (TextView) item.findViewById(R.id.pending_rating_list_item_username_textview);
	RatingBar ratingBar = (RatingBar) item.findViewById(R.id.pending_rating_list_item_ratingbar);
	TextView ratingerUserText = (TextView) item.findViewById(R.id.pending_rating_list_item_comment_textview);
	TextView ratingedClubName = (TextView) item.findViewById(R.id.pending_rating_list_item_clubname_textview);

	ratingerUserName.setText(actualRating.userName);
	ratingBar.setRating(actualRating.value);
	ratingerUserText.setText(actualRating.comment);
	ratingedClubName.setText(actualRating.clubName);

	return item;
    }
}
