package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ClubMyReviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	final EditText reviewEditText = (EditText) findViewById(R.id.club_my_review_edittext_review);
	reviewEditText.setOnEditorActionListener(new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			boolean handled = false;
			if(actionId == EditorInfo.IME_ACTION_DONE) {
				reviewEditText.clearFocus();
				handled = true;
			}
			return handled;
		}
	});
	setContentView(R.layout.activity_club_my_review);
    }

}
