package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ClubsSearchActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	Log.i("asdasd","onCreate");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dialog_clubs_search);
	findViewById(R.id.dialog_clubs_search_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
	Log.i("asdasd","onClick");
	finish();
	/*switch (v.getId()) {
	case R.id.dialog_clubs_search_button:
	    finish();
	    break;
	}*/
    }

}
