package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile);
    }

    public void onClickHandler(View v) {
	Intent i;
	switch (v.getId()) {
	case R.id.profile_button_modify:
	    i = new Intent(this,ProfileModifyActivity.class);
	    startActivity(i);
	    break;
	    
	case R.id.profile_button_password:
	    i = new Intent(this,ProfilePasswordActivity.class);
	    startActivity(i);
	    break;

	default:
	    break;
	}
    }

}
