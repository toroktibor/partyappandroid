package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class ProfilePasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile_password);
    }

    public void onClickHandler(View v){
    	switch (v.getId()) {
		case R.id.profile_password_button_save:
			finish();
			break;
		}  	
    }

}
