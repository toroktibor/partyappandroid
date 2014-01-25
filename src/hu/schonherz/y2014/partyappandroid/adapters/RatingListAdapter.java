package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class RatingListAdapter extends ArrayAdapter<Rating> {
	public RatingListAdapter(Context context, Rating[] objects) {
		super(context, R.layout.rating_list_item, objects);
		// TODO Auto-generated constructor stub
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if (item == null) {
		    LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
		    item = inflater.inflate(R.layout.rating_list_item, parent, false);
		}
		
		Rating actualRating = (Rating) getItem(position);

		TextView ratingerUserName = (TextView) item.findViewById(R.id.rating_list_item_user_name);
		RatingBar ratingBar = (RatingBar) item.findViewById(R.id.rating_list_item_ratingbar);
		TextView ratingerUserText = (TextView) item.findViewById(R.id.rating_list_item_text);
		
		ratingerUserName.setText(actualRating.userName);
		ratingBar.setRating(actualRating.value);
		ratingerUserText.setText(actualRating.comment);
		
		return item;
	    }
}
