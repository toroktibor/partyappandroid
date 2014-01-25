package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ClubRatingAdd extends ActionBarActivity {
	
	Rating actualRating;
	
	RatingBar ratingBar;
	EditText commentEditText;
	int clubListPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_club_rating_add);
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.rating_list_actionbar);
		
		clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
		actualRating = Session.getSearchViewClubs().get(clubListPosition).isRatingThisUser(Session.getActualUser().getId());
		
		Button actionbarAddButton = (Button) findViewById(R.id.rating_list_actionbar_add_button);
		actionbarAddButton.setVisibility(View.INVISIBLE);
		
		ratingBar = (RatingBar) findViewById(R.id.club_rating_add_ratingbar);
		commentEditText = (EditText) findViewById(R.id.club_rating_add_user_comment_edittext);
		
		if(actualRating!=null){
			ratingBar.setRating(actualRating.value);
			commentEditText.setText(actualRating.comment);
		}
		
		Button sendRating = (Button) findViewById(R.id.club_rating_add_rating_button);
		sendRating.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String comment = commentEditText.getText().toString();
				Float value = ratingBar.getRating();
				
				if(comment.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "Nem véleményezted a helyet!",  Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				
				if(actualRating == null){
					actualRating = new Rating(Session.getActualUser().getId(), Session.getActualUser().getNickname(), value, comment, 0);
					Session.getSearchViewClubs().get(clubListPosition).ratings.add(actualRating);
				} else {
					actualRating.value=value;
					actualRating.comment=comment;
				}
				finish();
			}
		});
	}

}
