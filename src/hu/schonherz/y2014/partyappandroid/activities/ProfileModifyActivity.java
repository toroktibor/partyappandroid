package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class ProfileModifyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_profile_modify);
    }


    public void onClickHandler(View v){
    	switch (v.getId()) {
		case R.id.profile_modify_button_save:
			finish();
			break;
		}  	
    }

}
