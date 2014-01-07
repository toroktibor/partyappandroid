package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);
    }

    public void onClickHandler(View v){
	if( v.getId() == R.id.register_button_register ){
	    
	    finish();
	}
    }
    
}
